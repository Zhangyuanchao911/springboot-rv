package com.xdd.springrvmp.rv.dao;

import com.xdd.springrvmp.rv.model.SysPerm;

public interface SysPermDao {
    int deleteByPrimaryKey(String permId);

    int insert(SysPerm record);

    int insertSelective(SysPerm record);

    SysPerm selectByPrimaryKey(String permId);

    int updateByPrimaryKeySelective(SysPerm record);

    int updateByPrimaryKey(SysPerm record);
}