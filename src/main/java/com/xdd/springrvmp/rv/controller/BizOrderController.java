package com.xdd.springrvmp.rv.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.BizCarDao;
import com.xdd.springrvmp.rv.dao.BizOrderDao;
import com.xdd.springrvmp.rv.dao.SysUserDao;
import com.xdd.springrvmp.rv.model.BizCar;
import com.xdd.springrvmp.rv.model.BizOrder;
import com.xdd.springrvmp.rv.model.SysUser;
import com.xdd.springrvmp.rv.model.vo.Order;
import com.xdd.springrvmp.rv.service.BizOrderService;
import com.xdd.springrvmp.utils.Constant;
import com.xdd.springrvmp.utils.RvLog;
import com.xdd.springrvmp.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@RequestMapping("/rv/biz-order")
public class BizOrderController {

    @Autowired
    private BizOrderService bizOrderService;
    @Autowired
    private BizOrderDao bizOrderDao;
    @Autowired
    private BizCarDao bizCarDao;
    @Autowired
    private SysUserDao sysUserDao;


    /**
     * 按订单状态查询
     *
     * @param state
     * @return
     */
    @RequestMapping("/listbystate")
    public String getListByState(HttpSession session, Model model, String state,
                                 @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        //获取当前登录用户的角色
        SysUser loginuser = (SysUser) session.getAttribute("loginuser");
        String userId = loginuser.getUserId();
        String roleId = loginuser.getRoleId();
        List<BizCar> carList = bizCarDao.selectList(new QueryWrapper<BizCar>().eq("is_deleted", false));
        IPage<BizOrder> page = null;
        //管理员可以查看所有订单
        if (roleId.equals(Constant.SUPER_ADMIN_ID)) {
            List<Order> orders = new ArrayList<>();
            page = bizOrderDao.selectByPage(new Page(pageNum, pageSize), null, state, null);
            List<BizOrder> orderList = page.getRecords();
            for (BizOrder order : orderList
            ) {
                Order newOrder = new Order();
                newOrder.setOrder(order);
                BizCar car = bizCarDao.selectByPrimaryKey(order.getCarId());
                SysUser user = sysUserDao.selectByPrimaryKey(order.getUserId());
                //添加User 和 Car信息
                if (car != null && user != null) {
                    newOrder.setCar(car);
                    newOrder.setUser(user);
                }
                //计算天数
                Long day = (order.getEndTime().getTime() - order.getStartTime().getTime()) / (24 * 60 * 60 * 1000);
                newOrder.setDay(day.toString());
                orders.add(newOrder);
            }

            model.addAttribute("orders", orders);
        } else {
            //普通管理员
            List<Order> orders = new ArrayList<>();
            page = bizOrderDao.selectByPage(new Page(pageNum, pageSize), userId, state, null);
            List<BizOrder> orderList = page.getRecords();
            for (BizOrder order : orderList
            ) {
                Order newOrder = new Order();
                newOrder.setOrder(order);
                BizCar car = bizCarDao.selectByPrimaryKey(order.getCarId());
                SysUser user = sysUserDao.selectByPrimaryKey(order.getUserId());
                if (car != null && user != null) {
                    newOrder.setCar(car);
                    newOrder.setUser(user);
                }
                Long day = (order.getEndTime().getTime() - order.getStartTime().getTime()) / (24 * 60 * 60 * 1000);
                newOrder.setDay(day.toString());
                orders.add(newOrder);
            }

            model.addAttribute("orders", orders);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        return "view/order/order_list";
    }


    /**
     * 查询所有订单（条件: 登录管理员 userId） 可以进行模糊查询
     * 超级管理员 可以查看所有订单 普通管理员只能查看自己的订单
     *
     * @param session
     * @return
     */
    @RequestMapping("/listbyuser")
    public String listbyuser(HttpSession session, Model model, String kw,
                             @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "success", required = false) String success,
                             @RequestParam(value = "fail", required = false) String fail) {
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        //获取当前登录用户的角色
        SysUser loginuser = (SysUser) session.getAttribute("loginuser");
        String userId = loginuser.getUserId();
        String roleId = loginuser.getRoleId();
        List<BizCar> carList = bizCarDao.selectList(new QueryWrapper<BizCar>().eq("is_deleted", false));
        IPage<BizOrder> page = null;
        //管理员可以查看所有订单
        if (roleId.equals(Constant.SUPER_ADMIN_ID)) {
            List<Order> orders = new ArrayList<>();
            List<BizOrder> orderList = null;
            //判断是否为模糊查询 超级管理员查看所有订单
            if (StringUtils.isEmpty(kw)) {
                page = bizOrderDao.selectByPage(new Page(pageNum, pageSize), null, null, null);
                orderList = page.getRecords();
                //查询所有订单之前先判断订单是否租赁时间已到 更改订单的状态
                orderList.forEach(order -> {
                    //如果租赁已到结束时间 下午六点 并且不是已完成状态的订单 则订单状态为超时
                    Long time = order.getEndTime().getTime() + 18 * 60 * 60 * 1000 - new Date().getTime();
                    if (!order.getOrderState().equals(Constant.ORDER_FINISH)) {
                        if (time.intValue() <= 0) {
                            log.info("相差为：" + String.valueOf((order.getEndTime().getTime() + 14 * 60 * 60 * 1000 - new Date().getTime())));
                            log.info(order.getCustomerName() + "的订单" + order.getOrderId() + Constant.ORDER_TIME_OUT);
                            order.setOrderState(Constant.ORDER_TIME_OUT);
                            order.setUpdateTime(new Date());
                            bizOrderDao.updateByPrimaryKey(order);
                        }
                    }

                });
            } else {
                page = bizOrderDao.selectByPage(new Page(pageNum, pageSize), null, null, kw);
                orderList = page.getRecords();
            }
            for (BizOrder order : orderList
            ) {
                Order newOrder = new Order();
                newOrder.setOrder(order);
                BizCar car = bizCarDao.selectByPrimaryKey(order.getCarId());
                SysUser user = sysUserDao.selectByPrimaryKey(order.getUserId());
                //添加User 和 Car信息
                if (car != null && user != null) {
                    newOrder.setCar(car);
                    newOrder.setUser(user);
                }
                //计算天数
                Long day = (order.getEndTime().getTime() - order.getStartTime().getTime()) / (24 * 60 * 60 * 1000);
                newOrder.setDay(day.toString());
                orders.add(newOrder);
            }

            model.addAttribute("orders", orders);
        } else {
            //普通管理员
            List<Order> orders = new ArrayList<>();
            List<BizOrder> orderList = null;
            //判断是否模糊查询  普通管理员查看自己的订单
            if (StringUtils.isEmpty(kw)) {
                page = bizOrderService.selectByPage(new Page(pageNum, pageSize), userId, null, null);
                orderList = page.getRecords();
                //查询所有订单之前先判断订单是否租赁时间已到 更改订单的状态
                orderList.forEach(order -> {
                    //如果租赁已到结束时间 下午六点 并且不是已完成状态的订单 则订单状态为超时
                    Long time = order.getEndTime().getTime() + 18 * 60 * 60 * 1000 - new Date().getTime();
                    if (!order.getOrderState().equals(Constant.ORDER_FINISH)) {
                        if (time.intValue() <= 0) {
                            log.info("相差为：" + String.valueOf((order.getEndTime().getTime() + 14 * 60 * 60 * 1000 - new Date().getTime())));
                            log.info(order.getCustomerName() + "的订单" + order.getOrderId() + Constant.ORDER_TIME_OUT);
                            order.setOrderState(Constant.ORDER_TIME_OUT);
                            order.setUpdateTime(new Date());
                            bizOrderDao.updateByPrimaryKey(order);
                        }
                    }

                });
            } else {
                page = bizOrderDao.selectByPage(new Page(pageNum, pageSize), userId, null, kw);
                orderList = page.getRecords();

            }
            for (BizOrder order : orderList
            ) {
                Order newOrder = new Order();
                newOrder.setOrder(order);
                BizCar car = bizCarDao.selectByPrimaryKey(order.getCarId());
                SysUser user = sysUserDao.selectByPrimaryKey(order.getUserId());
                if (car != null && user != null) {
                    newOrder.setCar(car);
                    newOrder.setUser(user);
                }
                Long day = (order.getEndTime().getTime() - order.getStartTime().getTime()) / (24 * 60 * 60 * 1000);
                newOrder.setDay(day.toString());
                orders.add(newOrder);
            }

            model.addAttribute("orders", orders);
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
        return "view/order/order_list";
    }


    /**
     * 新增订单
     *
     * @param bizOrder
     * @return
     */
    @RvLog(value = "新增订单")
    @RequestMapping("/add")
    public String add(
            @RequestParam(value = "carId") String carId,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "customerName") String customerName,
            @RequestParam(value = "customerTel") String customerTel,
            @RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "remark") String remark,
            RedirectAttributes attributes
    ) throws ParseException {
        BizCar bizCar = bizCarDao.selectByPrimaryKey(carId);
        //格式化日期  将string类型转换为date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(startTime);
        Date end = sdf.parse(endTime);
        //计算租赁的时长
        Long startlong = start.getTime();
        Long endlong = end.getTime();
        Long day = (endlong - startlong) / (24 * 60 * 60 * 1000);
        log.info("day得到为" + day);
        BigDecimal bDay = new BigDecimal(day);
        if (day <= 0) {
            DateFormat format = DateFormat.getDateInstance();
            attributes.addAttribute("fail", "新增失败 租赁时间错误：从" + format.format(start) + "  到 " + format.format(end));
            return "redirect:/rv/biz-order/listbyuser";
        }

        //new BigDecimal(rental).multiply(bizCar.getCarPrice()) 计算租金为多少
        BizOrder bizOrder = new BizOrder(UUIDUtils.getUUId(), carId, bizCar.getCarPrice().multiply(bDay), bizCar.getCarDeposit(), start, end,
                customerName, customerTel, userId, Constant.ORDER_PAY_NO, remark, new Date(), new Date(), false);
        log.info("新增订单的信息：" + bizOrder);
        Integer flag = bizOrderService.add(bizOrder);
        if (flag != null && flag.equals(1)) {
            attributes.addAttribute("success", "新增成功");
            return "redirect:/rv/biz-order/listbyuser";
        } else {
            attributes.addAttribute("fail", "新增失败");
            return "redirect:/rv/biz-order/listbyuser";
        }


    }


    /**
     * 更新订单状态
     *
     * @param bizOrder
     * @return
     */
    @RvLog(value = "更新订单状态")
    @RequestMapping("/updatestate")
    public String updatestate(String state, String orderId, String userId, RedirectAttributes attributes) {
        BizOrder bizOrder = bizOrderDao.selectByPrimaryKey(orderId);
        bizOrder.setOrderState(state);
        bizOrder.setUpdateTime(new Date());
        if (bizOrderService.update(bizOrder).equals(1)) {
            attributes.addAttribute("success", "更新成功");
            return "redirect:/rv/biz-order/listbyuser";
        } else {

            attributes.addAttribute("fail", "更新失败");
            return "redirect:/rv/biz-order/listbyuser";
        }

    }

    /**
     * 逻辑删除订单
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id, RedirectAttributes attributes) {

        int flag = bizOrderDao.deleteByPrimaryKey(id);
        if (flag == 1) {
            attributes.addAttribute("success", "删除成功");
            return "redirect:/rv/biz-order/listbyuser";
        } else {
            attributes.addAttribute("fail", "删除成功");
            return "redirect:/rv/biz-order/listbyuser";
        }
    }

    @GetMapping("/exportexcel")
    public void exportExcel() {

    }
}
