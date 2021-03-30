package com.xdd.springrvmp.rv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserDao extends BaseMapper<SysUser> {
    int deleteByPrimaryKey(String userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByUserAccount(String account);

    IPage<SysUser> selectByPage(Page<SysUser> page, @Param("kw") String kw);

}