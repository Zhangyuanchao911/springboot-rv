package com.xdd.springrvmp.tourl;

import com.xdd.springrvmp.rv.dao.SysUserDao;
import com.xdd.springrvmp.rv.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * @Author AixLeft
 * Date 2021/1/14
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private SysUserDao sysUserDao;

    @RequestMapping("/login")
    public String login(@RequestParam String loginName, @RequestParam String password, Model model, HttpSession session) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        if (loginName.equals("") || loginName == null) {
            model.addAttribute("msg", "用户名不能为空");
            return "login";
        } else if (password.equals("") || password == null) {
            model.addAttribute("msg", "密码不能为空");
            return "login";
        } else {

            SysUser user = sysUserDao.selectByUserAccount(loginName);
            if (user != null) {
                session.setAttribute("loginuser", user);
            }

            //封装用户登录的信息
            UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
            try {
                subject.login(token); //执行登录方法
                return "redirect:index";
            } catch (AuthenticationException exception) {
                model.addAttribute("msg", "用户名或密码错误");
                return "login";
            }
        }
    }

    @RequestMapping("/loginOut")
    public String loginOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        log.info("退出登录");
        return "login";
    }
}
