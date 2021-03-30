package com.xdd.springrvmp.rv.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.SysLogDao;
import com.xdd.springrvmp.rv.dao.SysUserDao;
import com.xdd.springrvmp.rv.model.BizCar;
import com.xdd.springrvmp.rv.model.SysLog;
import com.xdd.springrvmp.rv.model.SysUser;
import com.xdd.springrvmp.rv.model.vo.Log;
import com.xdd.springrvmp.rv.service.SysLogService;
import com.xdd.springrvmp.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author AixLeft
 * Date 2021/2/2
 */
@Slf4j
@Controller
@RequestMapping("/rv/sys-log")
public class SysLogController {
    @Autowired
    private SysLogDao sysLogDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysLogService sysLogService;

    /**
     * 查询日志 以及模糊查询
     *
     * @param session
     * @param model
     * @param kw
     * @return
     */
    @RequestMapping("/orderLog")
    public String orderLog(HttpSession session, Model model, String kw,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        SysUser user = (SysUser) session.getAttribute("loginuser");
        List<SysLog> list = null;
        IPage<SysLog> page = null;
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }

        //超级管理员
        if (user.getRoleId().equals(Constant.SUPER_ADMIN_ID)) {
            page = sysLogService.selectPage(new Page<>(pageNum, pageSize));
            list = page.getRecords();

        } else {
            page = sysLogService.selectPageUserId(new Page<>(pageNum, pageSize), user.getUserId());
            list = page.getRecords();
        }
        List<Log> logList = new ArrayList<>();
        //如果没有进行模糊查询
        if (StringUtils.isEmpty(kw)) {
            for (SysLog sysLog : list) {
                Log newLog = new Log();
                newLog.setSysLog(sysLog);
                SysUser getUser = sysUserDao.selectByPrimaryKey(sysLog.getUserId());
                if (getUser != null) {
                    newLog.setSysUser(getUser);
                }
                logList.add(newLog);
            }
        } else {
            //模糊查询 把kw和json中的orderId进行比较 如果不相同则从list中remove
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOperation().equals("新增订单")) {
                    list.remove(i);
                }
            }
            List<SysLog> newlog = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) JSON.parse(list.get(i).getParams());
                System.out.println("map------" + map.get("orderId"));
                //如果得到的orderId和要查询的不相同 则将list中该条数据去除
                if (map.get("orderId") != null && map.get("orderId").equals(kw))
                    newlog.add(list.get(i));

            }
            for (SysLog sysLog : newlog) {
                Log newLog = new Log();
                newLog.setSysLog(sysLog);
                SysUser getUser = sysUserDao.selectByPrimaryKey(sysLog.getUserId());
                if (getUser != null) {
                    newLog.setSysUser(getUser);
                }
                logList.add(newLog);
            }

        }
        List<Integer> listPage = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            listPage.add(j);
        }
        model.addAttribute("list", listPage);
        model.addAttribute("pageInfo", page);
        model.addAttribute("logs", logList);
        return "view/order/order_log";
    }

    /**
     * 日志信息导出
     */
    @GetMapping("/exportListExcel")
    public void exportListExcel(HttpSession session) {
        SysUser sysUser = (SysUser) session.getAttribute("loginuser");
        List<SysLog> sysLogs = null;
        //判断是否为超管 获取全部用户日志
        if (sysUser.getRoleId().equals(Constant.SUPER_ADMIN_ID)) {
            sysLogs = sysLogDao.selectList(new QueryWrapper<SysLog>());
        } else {
            sysLogs = sysLogDao.selectList(new QueryWrapper<SysLog>().eq("user_id", sysUser.getUserId()));
        }
        List<Log> logList = new ArrayList<>();
        sysLogs.forEach(logs -> {
            Log log = new Log();
            log.setSysLog(logs);
            log.setSysUser(sysUserDao.selectByPrimaryKey(logs.getUserId()));
            logList.add(log);
        });
        sysLogService.exportListExcel(logList);

    }

}
