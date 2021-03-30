package com.xdd.springrvmp.rv.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.SysUser;

/**
 * @Author AixLeft
 * Date 2021/1/18
 */

public interface SysUserService {
    Integer delete(String userId);
    SysUser selectById(String userId);
    IPage<SysUser> selectByPage(Page<SysUser> page, String kw);
}
