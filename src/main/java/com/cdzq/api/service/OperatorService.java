package com.cdzq.api.service;

import com.cdzq.api.entity.Jbr;
import com.cdzq.api.entity.JbrLog;
import com.cdzq.api.entity.Whiteip;
import com.cdzq.api.mapper.JbrLogMapper;
import com.cdzq.api.mapper.JbrMapper;
import com.cdzq.api.mapper.WhiteipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
     * 查询IP是否在白名单
     * @param ip
     * @return
     */
    @Cacheable(value="jbr_whiteip",key="#ip")
    public int getWhiteIp(String ip){
        Whiteip whiteip = new Whiteip();
        whiteip.setIp(ip);
        whiteip.setYwlx("jbr");
        return whiteipMapper.selectCount(whiteip);
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
}
