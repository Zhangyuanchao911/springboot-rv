package com.xdd.springrvmp.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xdd.springrvmp.rv.dao.SysPermDao;
import com.xdd.springrvmp.rv.dao.SysRoleDao;
import com.xdd.springrvmp.rv.dao.SysRolePermDao;
import com.xdd.springrvmp.rv.dao.SysUserDao;
import com.xdd.springrvmp.rv.model.SysPerm;
import com.xdd.springrvmp.rv.model.SysRole;
import com.xdd.springrvmp.rv.model.SysRolePerm;
import com.xdd.springrvmp.rv.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author AixLeft
 * Date 2021/1/14
 * <p>
 * 自定义的UserRealm
 */

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRolePermDao sysRolePermDao;
    @Autowired
    private SysPermDao sysPermDao;
    private Logger logger = LoggerFactory.getLogger(UserRealm.class);

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        logger.info("执行授权=>doGetAuthorizationInfo");
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
        SysRole sysRole = sysRoleDao.selectByPrimaryKey(sysUser.getRoleId());
        if (null == sysUser) {
            return null;
        }
        info.addRole(sysRole.getRole());
        //获取该角色的资源类list
        List<SysRolePerm> rolePermList = sysRolePermDao.selectList(new QueryWrapper<SysRolePerm>().eq("role_id", sysUser.getRoleId()));
        List<SysPerm> permList = new ArrayList<>();
        rolePermList.forEach(list -> {
            permList.add(sysPermDao.selectByPrimaryKey(list.getPermId()));
        });
        //添加到权限中
        for (SysPerm perm : permList) {
            info.addStringPermission(perm.getPermission());
        }
        logger.info(sysUser.toString());

        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("执行认证=>doGetAuthenticationInfo");
        String account = token.getUsername();
        logger.info("登陆用户为:  " + account);

        SysUser user = sysUserDao.selectByUserAccount(account);
        // 盐值 使用 username作为盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserAccount());
        //账户认证
        if (user == null || !account.equals(user.getUserAccount())) {
            logger.info("用户名错误");
            return null; //UnknowAccountExecption
        } else {
            //密码认证，将正确数据给 shiro处理
            return new SimpleAuthenticationInfo(user, user.getUserPassword(), credentialsSalt, getName());
        }

    }

}
