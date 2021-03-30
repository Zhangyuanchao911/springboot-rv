package com.xdd.springrvmp.rv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xdd.springrvmp.rv.model.SysRolePerm;

public interface SysRolePermDao extends BaseMapper<SysRolePerm> {
    int deleteByPrimaryKey(String sysRolePermId);

    int insert(SysRolePerm record);

    int insertSelective(SysRolePerm record);

    SysRolePerm selectByPrimaryKey(String sysRolePermId);

    int updateByPrimaryKeySelective(SysRolePerm record);

    int updateByPrimaryKey(SysRolePerm record);
}