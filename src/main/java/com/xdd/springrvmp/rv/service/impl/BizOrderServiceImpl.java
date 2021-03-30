package com.xdd.springrvmp.rv.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.BizCarDao;
import com.xdd.springrvmp.rv.dao.BizOrderDao;
import com.xdd.springrvmp.rv.model.BizCar;
import com.xdd.springrvmp.rv.model.BizOrder;
import com.xdd.springrvmp.rv.model.vo.ShowOrder;
import com.xdd.springrvmp.rv.service.BizOrderService;
import com.xdd.springrvmp.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author AixLeft
 * Date 2021/1/19
 */
@Service
@Transactional
public class BizOrderServiceImpl implements BizOrderService {
    private Logger logger = LoggerFactory.getLogger(BizOrderServiceImpl.class);
    @Autowired
    private BizOrderDao bizOrderDao;
    @Autowired
    private BizCarDao bizCarDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(BizOrder bizOrder) {
        int flag = 0;
        //通过获取当前订单状态 改变房车的租赁状态
        String state = bizOrder.getOrderState();
        String carId = bizOrder.getCarId();
        //当订单为‘完成’状态 将该汽车状态改为 未租赁
        if (state.equals(Constant.ORDER_FINISH)) {
            BizCar bizCar = bizCarDao.selectByPrimaryKey(carId);
            bizCar.setUpdateTime(new Date());
            bizCar.setState(Constant.LEASED_NO);
            bizCarDao.updateByPrimaryKeySelective(bizCar);
        }
        flag = bizOrderDao.updateByPrimaryKeySelective(bizOrder);
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(BizOrder bizOrder) {
        //通过订单查询carId和状态情况
        String carId = bizOrder.getCarId();
        BizCar car = bizCarDao.selectByPrimaryKey(carId);
        //车辆信息不存在 或者已租赁
        if (car == null || car.getState().equals(Constant.LEASED_YES)) {
            logger.warn("新增订单失败！！！！");
            return null;
        }
        //可以新增订单
        else {
            BizCar bizCar = bizCarDao.selectByPrimaryKey(carId);
            bizCar.setUpdateTime(new Date());
            bizCar.setState(Constant.LEASED_YES);
            bizCarDao.updateByPrimaryKeySelective(bizCar);
            return bizOrderDao.insert(bizOrder);
        }

    }

    @Override
    public IPage<BizOrder> selectByPage(Page<BizOrder> page, String userId, String state, String kw) {
        return bizOrderDao.selectByPage(page, userId, state, kw);
    }

    @Override
    public Map<String, int []> showOrder() {
        List<ShowOrder> orderList = bizOrderDao.showOrder();
        Set<String> brandName = new HashSet<>();
        for (ShowOrder showOrder : orderList
        ) {
            brandName.add(showOrder.getBrandName());
        }
        int [] a = new int[12];
        Map<String, int[]> map = new HashMap<>();
        for (String s : brandName) {
            map.put(s, new int[12]);
        }
        for (int i = 0; i < orderList.size(); i++) {
            if (map.containsKey(orderList.get(i).getBrandName())) {
                int[] value = map.get(orderList.get(i).getBrandName());
                value[Integer.valueOf(orderList.get(i).getMonth()) - 1]=Integer.valueOf(orderList.get(i).getRental());
                map.replace(orderList.get(i).getBrandName(), value);
            }
        }
        return map;
    }
}
