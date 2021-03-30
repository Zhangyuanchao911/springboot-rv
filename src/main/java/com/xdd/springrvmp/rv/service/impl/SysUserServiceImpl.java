package com.xdd.springrvmp.rv.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.SysUserDao;
import com.xdd.springrvmp.rv.model.SysUser;
import com.xdd.springrvmp.rv.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author AixLeft
 * Date 2021/1/18
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public Integer delete(String id) {
        return sysUserDao.deleteByPrimaryKey(id);
    }

    @Override
    public SysUser selectById(String id) {
        return sysUserDao.selectByPrimaryKey(id);
    }

    @Override
    public IPage<SysUser> selectByPage(Page<SysUser> page, String kw) {
        return sysUserDao.selectByPage(page, kw);
    }
}
