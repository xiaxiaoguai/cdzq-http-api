package com.cdzq.api.service;

import com.cdzq.api.base.ResultData;
import com.cdzq.api.entity.Jbr;
import com.cdzq.api.entity.JbrLog;
import com.cdzq.api.entity.Whiteip;
import com.cdzq.api.mapper.JbrLogMapper;
import com.cdzq.api.mapper.JbrMapper;
import com.cdzq.api.mapper.WhiteipMapper;
import com.cdzq.api.util.ExampleUtil;
import com.cdzq.api.util.JqueryUiJson;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorService {
    private final JbrMapper jbrMapper;
    private final JbrLogMapper jbrLogMapper;
    private final WhiteipMapper whiteipMapper;

    /**
     * 根据营业部号获取经办人列表
     * @param brach_no
     * @return
     */
    @Cacheable(value="jbr_brach_no",key="#brach_no")
    public List<Jbr> getJbrByBrach(int brach_no){
        return jbrMapper.getJbrByBrach(brach_no);
    }

    /**
     * 查询IP白名单列表
     * @param ywlx
     * @return
     */
    @Cacheable(value="jbr_whiteip",key="#ywlx")
    public String getWhiteIp(String ywlx){
        String result=null;
        Whiteip wi = new Whiteip();
        wi.setYwlx(ywlx);
        List<Whiteip> whiteip_list=whiteipMapper.select(wi);
        for (Whiteip whiteip : whiteip_list) {
            result=result==null?whiteip.getIp():result+";"+whiteip.getIp();
        }
        return result;
    }

    /**
     * 异步插入日志
     * @param jbrLog
     */
    @Async
    public void insertLog(JbrLog jbrLog,String ip){
        jbrLog.setSysip(ip);
        jbrLog.setSystime(new Date());
        jbrLogMapper.insert(jbrLog);

    }

    /**
     * 分页获取经办人列表
     * @param request
     */
    public JqueryUiJson jbrList(HttpServletRequest request) {
        // 拼装查询条件
        Example example = ExampleUtil.getExample(Jbr.class, request);
        // 分页
        PageHelper.startPage(ExampleUtil.getPage(request), ExampleUtil.getRows(request));
        // 查询
        List<Jbr> jbrlist = jbrMapper.selectByExample(example);
        // 返回结果
        JqueryUiJson jqueryUiJson = new JqueryUiJson(ExampleUtil.getPageInfo(jbrlist).getTotal(), jbrlist);
        return jqueryUiJson;
    }

    /**
     * 查询经办人
     * @param id
     * @return
     */
    public Jbr queryJbrById(Integer id) {
        return jbrMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询白名单
     * @param id
     * @return
     */
    public Whiteip queryWhiteipById(Integer id) {
        return whiteipMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加经办人
     * @param jbr
     * @return
     */
    @CacheEvict(value="jbr_brach_no",key="#jbr.brachNo")
    public ResultData addJbr(Jbr jbr) {
        jbrMapper.insert(jbr);
        return ResultData.ok();
    }

    /**
     * 修改经办人
     * @param jbr
     * @return
     */
    @CacheEvict(value="jbr_brach_no",key="#jbr.brachNo")
    public ResultData updateJbr(Jbr jbr) {
        jbrMapper.updateByPrimaryKeySelective(jbr);
        return ResultData.ok();
    }

    /**
     * 删除经办人
     * @param jbr
     * @return
     */
    @CacheEvict(value="jbr_brach_no",key="#jbr.brachNo")
    public ResultData deleteJbr(Jbr jbr) {
        jbrMapper.deleteByPrimaryKey(jbr.getId());
        return ResultData.ok();
    }

    /**
     * 分页获取接口调用日志
     * @param request
     */
    public JqueryUiJson jbrLog(HttpServletRequest request) {
        // 拼装查询条件
        Example example = ExampleUtil.getExample(JbrLog.class, request);
        // 分页
        PageHelper.startPage(ExampleUtil.getPage(request), ExampleUtil.getRows(request),"id desc");
        // 查询
        List<JbrLog> jbrloglist = jbrLogMapper.selectByExample(example);
        // 返回结果
        JqueryUiJson jqueryUiJson = new JqueryUiJson(ExampleUtil.getPageInfo(jbrloglist).getTotal(), jbrloglist);
        return jqueryUiJson;
    }

    /**
     * 分页获取白名单
     * @param request
     */
    public JqueryUiJson whiteIpList(HttpServletRequest request) {
        // 拼装查询条件
        Example example = ExampleUtil.getExample(Whiteip.class, request);
        // 分页
        PageHelper.startPage(ExampleUtil.getPage(request), ExampleUtil.getRows(request),"ip");
        // 查询
        List<Whiteip> whiteiplist = whiteipMapper.selectByExample(example);
        // 返回结果
        JqueryUiJson jqueryUiJson = new JqueryUiJson(ExampleUtil.getPageInfo(whiteiplist).getTotal(), whiteiplist);
        return jqueryUiJson;
    }

    /**
     * 添加白名单
     * @param whiteip
     * @return
     */
    @CacheEvict(value="jbr_whiteip",allEntries=true)
    public ResultData addWhiteip(Whiteip whiteip) {
        whiteip.setYwlx("jbr");
        whiteipMapper.insert(whiteip);
        return ResultData.ok();
    }

    /**
     * 删除白名单
     * @param whiteip
     * @return
     */
    @CacheEvict(value="jbr_whiteip",allEntries=true)
    public ResultData deleteWhiteip(Whiteip whiteip) {
        whiteipMapper.deleteByPrimaryKey(whiteip.getId());
        return ResultData.ok();
    }

    /**
     * 清空经办人缓存
     * @return
     */
    @CacheEvict(value="jbr_brach_no",allEntries=true)
    public void delCacheJbr() {}

    /**
     * 清空白名单缓存
     * @return
     */
    @CacheEvict(value="jbr_whiteip",allEntries=true)
    public void delCacheWhiteip() {}
}
