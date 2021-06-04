package com.cdzq.api.controller;

import com.cdzq.api.base.ResultData;
import com.cdzq.api.entity.Jbr;
import com.cdzq.api.entity.JbrLog;
import com.cdzq.api.entity.Whiteip;
import com.cdzq.api.service.OperatorService;
import com.cdzq.api.util.JqueryUiJson;
import com.cdzq.api.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("operator")
@Api(tags = "经办人模块")
public class OperatorController {

    private final OperatorService operatorService;

    @ApiOperation(value = "根据营业部编码获取经办人列表")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "JbrLog",name = "jbrLog")
    })
    @GetMapping("findbybrach")
    public ResultData FindByBrach(HttpServletRequest request,@Validated JbrLog jbrLog){
        String ip = IpUtils.getIp(request);
        String white_ip_list=operatorService.getWhiteIp("jbr");
        if(IpUtils.checkLoginIP(ip,white_ip_list)==false){
            jbrLog.setProdCode("ip:"+ip+"禁止访问");
            operatorService.insertLog(jbrLog,ip);
            return ResultData.error().message("ip:"+ip+"禁止访问");
        }
        List<Jbr> jbrs = operatorService.getJbrByBrach(jbrLog.getBrachNo());
        operatorService.insertLog(jbrLog,ip);
        return ResultData.ok().data("jbrlist",jbrs);
    }

    @ApiOperation(value = "经办人分页查询")
    @PostMapping("jbrlist")
    public JqueryUiJson jbrList(HttpServletRequest request) {
        return operatorService.jbrList(request);
    }

    @ApiOperation(value = "经办人增加")
    @PostMapping("addjbr")
    public ResultData addJbr(@Validated Jbr jbr) {
        return operatorService.addJbr(jbr);
    }

    @ApiOperation(value = "经办人修改")
    @PostMapping("updatejbr")
    public ResultData updateJbr(@Validated Jbr jbr) {
        return operatorService.updateJbr(jbr);
    }

    @ApiOperation(value = "经办人删除")
    @PostMapping("deletejbr")
    public ResultData deleteJbr(Integer id) {
        Jbr jbr=operatorService.queryJbrById(id);
        return operatorService.deleteJbr(jbr);
    }

    @ApiOperation(value = "调用接口日志分页查询")
    @PostMapping("jbrlog")
    public JqueryUiJson jbrLog(HttpServletRequest request) {
        return operatorService.jbrLog(request);
    }

    @ApiOperation(value = "白名单增加")
    @PostMapping("addwhiteip")
    public ResultData addWhiteip(@Validated Whiteip whiteip) {
        return operatorService.addWhiteip(whiteip);
    }

    @ApiOperation(value = "白名单删除")
    @PostMapping("deletewhiteip")
    public ResultData deleteWhiteip(Integer id) {
        Whiteip whiteip=operatorService.queryWhiteipById(id);
        return operatorService.deleteWhiteip(whiteip);
    }

    @ApiOperation(value = "白名单分页查询")
    @PostMapping("whiteiplist")
    public JqueryUiJson whiteIpList(HttpServletRequest request) {
        return operatorService.whiteIpList(request);
    }

    @ApiOperation(value = "清理经办人缓存")
    @GetMapping("delcachejbr")
    public ResultData delCacheJbr() {
        operatorService.delCacheJbr();
        return ResultData.ok();
    }

    @ApiOperation(value = "清理白名单缓存")
    @GetMapping("delcachewhiteip")
    public ResultData delCacheWhiteip() {
        operatorService.delCacheWhiteip();
        return ResultData.ok();
    }

}
