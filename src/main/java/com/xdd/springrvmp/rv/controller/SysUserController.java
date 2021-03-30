package com.xdd.springrvmp.rv.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.SysRoleDao;
import com.xdd.springrvmp.rv.dao.SysUserDao;
import com.xdd.springrvmp.rv.model.SysRole;
import com.xdd.springrvmp.rv.model.SysUser;
import com.xdd.springrvmp.rv.model.vo.User;
import com.xdd.springrvmp.rv.service.SysUserService;
import com.xdd.springrvmp.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.xdd.springrvmp.rv.model.SysRole;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author AixLeft
 * @since 2021-01-18
 */
@Slf4j
@Controller
@RequestMapping("/rv/sys-user")
public class SysUserController {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleDao sysRoleDao;

    /**
     * 查询所有用户 （超级管理员）
     *
     * @return
     */
    @RequiresPermissions("superadmin:list")
    @RequestMapping("/list")
    public String getList(Model model,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "success", required = false) String success,
                          @RequestParam(value = "fail", required = false) String fail) {
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<SysUser> page = sysUserDao.selectByPage(new Page<>(pageNum, pageSize), null);
        List<SysUser> userList = page.getRecords();
        List<SysRole> roleList = sysRoleDao.selectList(new QueryWrapper<SysRole>().eq("is_deleted", false));
        List<User> users = new ArrayList<>();
        for (SysUser user : userList
        ) {
            User userVo = new User();
            userVo.setUser(user);
            for (SysRole role : roleList) {
                if (user.getRoleId().equals(role.getRoleId())) {
                    userVo.setRole(role);
                }
            }
            users.add(userVo);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("success", success);
        model.addAttribute("fail", fail);
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("users", users);
        return "view/user/user_list";
    }

    @RequestMapping("/checkaccount")
    @ResponseBody
    public String checkaccount(String userAccount) {
        JSONObject result = new JSONObject();
        Integer flag = sysUserDao.selectCount(new QueryWrapper<SysUser>().eq("is_deleted", false).eq("user_account", userAccount));
        if (flag != null && flag != 0) {
            result.put("valid", false);
        } else {
            result.put("valid", true);
        }
        return result.toJSONString();
    }

    /**
     * 通过用户名 模糊查询
     *
     * @param fw
     * @return
     */
    @RequestMapping("/fuzzyList")
    public String fuzzyList(@RequestParam(value = "kw") String kw, Model model,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<SysUser> page = sysUserDao.selectByPage(new Page<>(pageNum, pageSize), kw);
        List<SysUser> userList = page.getRecords();
        List<SysRole> roleList = sysRoleDao.selectList(new QueryWrapper<SysRole>().eq("is_deleted", false));
        List<User> users = new ArrayList<>();
        for (SysUser user : userList
        ) {
            User userVo = new User();
            userVo.setUser(user);
            for (SysRole role : roleList) {
                if (user.getRoleId().equals(role.getRoleId())) {
                    userVo.setRole(role);
                }
            }
            users.add(userVo);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("users", users);
        return "view/user/user_list";
    }

    /**
     * 通过Id查询用户 并将用户信息和角色信息添加到model中
     *
     * @param userId
     * @return
     */
    @RequiresPermissions("superadmin:findbyid")
    @RequestMapping("/findbyid")
    public String findById(@RequestParam(value = "id") String id, Model model) {
        SysUser sysUser = sysUserService.selectById(id);
        SysRole sysRole = sysRoleDao.selectByPrimaryKey(sysUser.getRoleId());
        User users = new User();
        users.setUser(sysUser);
        users.setRole(sysRole);
        model.addAttribute("users", users);

        List<SysRole> roleList = sysRoleDao.selectList(new QueryWrapper<SysRole>().eq("is_deleted", false));
        model.addAttribute("rolelist", roleList);
        return "view/user/user_modify";
    }

    /**
     * 通过登录用户查询信息
     *
     * @param model
     * @param session
     * @return
     */
    @RequiresPermissions(value = {"admin:findbyloginuser", "superadmin:findbyloginuser"}, logical = Logical.OR)
    @RequestMapping("/findbyloginuser")
    public String findByLoginUser(Model model, HttpSession session) {
        SysUser sysUser = (SysUser) session.getAttribute("loginuser");
        String userId = sysUser.getUserId();
        SysUser newUser = sysUserDao.selectByPrimaryKey(userId);
        log.info("session值" + sysUser.toString());
        SysRole sysRole = sysRoleDao.selectByPrimaryKey(newUser.getRoleId());
        User users = new User();
        users.setUser(newUser);
        users.setRole(sysRole);
        model.addAttribute("users", users);

        List<SysRole> roleList = sysRoleDao.selectList(new QueryWrapper<SysRole>().eq("is_deleted", false));
        model.addAttribute("rolelist", roleList);
        return "view/user/user_modify";
    }

    /**
     * 新增用户
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/add")
    public String add(SysUser sysUser, RedirectAttributes attributes) {
        //设置盐值
        Object salt = ByteSource.Util.bytes(sysUser.getUserAccount());
        String newPassword = new SimpleHash("MD5", sysUser.getUserPassword(), salt, 1024).toHex();
        log.info("newPassword" + newPassword);
        sysUser.setUserPassword(newPassword);
        sysUser.setUserId(UUIDUtils.getUUId());
        sysUser.setCreatTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setIsDeleted(false);
        log.info("新增的数据为" + sysUser.toString());
        SysUser user = sysUserDao.selectOne(new QueryWrapper<SysUser>().eq("is_deleted", false).eq("user_account", sysUser.getUserAccount()));
        if (null == user || !user.getUserAccount().equals(sysUser.getUserAccount())) {
            sysUserDao.insertSelective(sysUser);
            attributes.addAttribute("success", "新增成功");
            return "redirect:/rv/sys-user/list";
        } else {
            attributes.addAttribute("fail", "新增失败");
            return "redirect:/rv/sys-user/list";
        }

    }

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/update")
    public String update(SysUser sysUser, RedirectAttributes attributes) {
        SysUser oldUser = sysUserDao.selectByUserAccount(sysUser.getUserAccount());
        //判断用户修改密码 新旧密码不同
        if (!oldUser.getUserPassword().equals(sysUser.getUserPassword())) {
            //设置盐值
            Object salt = ByteSource.Util.bytes(sysUser.getUserAccount());
            String newPassword = new SimpleHash("MD5", sysUser.getUserPassword(), salt, 1024).toHex();
            log.info("newPassword" + newPassword);
            sysUser.setUserPassword(newPassword);
        }

        sysUser.setUpdateTime(new Date());
        int flag = sysUserDao.updateByPrimaryKeySelective(sysUser);
        if (flag == 1) {
            attributes.addAttribute("success", "修改成功");
            return "redirect:/rv/sys-user/list";
        } else {
            attributes.addAttribute("fail", "修改失败");
            return "redirect:/rv/sys-user/list";
        }

    }

    /**
     * 逻辑删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "id") String id, RedirectAttributes attributes) {
        if (StringUtils.isEmpty(id)) {
            attributes.addAttribute("fail","删除失败");
            return "redirect:/rv/sys-user/list";
        } else {
            sysUserDao.deleteByPrimaryKey(id);
            attributes.addAttribute("success","删除成功");
            return "redirect:/rv/sys-user/list";
        }

    }

}
