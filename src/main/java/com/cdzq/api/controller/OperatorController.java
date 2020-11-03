package com.cdzq.api.controller;

import com.cdzq.api.base.ResultData;
import com.cdzq.api.entity.Jbr;
import com.cdzq.api.entity.JbrLog;
import com.cdzq.api.service.OperatorService;
import com.cdzq.api.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("operator")
@Api(tags = "经办人模块")
public class OperatorController {

    private final OperatorService operatorService;

    @ApiOperation(value = "获取经办人列表")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "JbrLog",name = "jbrLog")
    })
    @GetMapping("findbybrach")
    public ResultData FindByBrach(HttpServletRequest request,@Validated JbrLog jbrLog){
        String ip = StringUtils.getIp(request);
        if(operatorService.getWhiteIp(ip) == 0){
            return ResultData.error().message("ip:"+ip+"禁止访问");
        }
        List<Jbr> jbrs = operatorService.getJbrByBrach(jbrLog.getBrachNo());
        operatorService.insertLog(jbrLog,ip);
        return ResultData.ok().data("jbrlist",jbrs);
    }

}
