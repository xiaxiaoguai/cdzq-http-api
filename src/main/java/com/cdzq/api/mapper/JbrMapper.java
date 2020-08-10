package com.cdzq.api.mapper;

import com.cdzq.api.base.MyMapper;
import com.cdzq.api.entity.Jbr;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface JbrMapper extends MyMapper<Jbr> {

    @Select("select id,jbr_id as jbrId,brach_no as brachNo,jbr_name as jbrName,jbr_zs as jbrZs from jbr where brach_no=#{brach_no}")
    List<Jbr> getJbrByBrach(Integer brach_no);
}