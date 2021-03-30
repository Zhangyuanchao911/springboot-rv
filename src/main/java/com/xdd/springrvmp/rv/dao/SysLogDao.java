package com.xdd.springrvmp.rv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.SysLog;
import org.apache.ibatis.annotations.Param;

public interface SysLogDao extends BaseMapper<SysLog> {
    int deleteByPrimaryKey(String id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    IPage<SysLog> selectByPage(Page page);

    IPage<SysLog> selectByPageUserId(Page page, @Param(value = "userId") String userId);
}