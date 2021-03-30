package com.xdd.springrvmp.rv.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.BizOrder;

import java.util.Map;

/**
 * @Author AixLeft
 * Date 2021/1/19
 */
public interface BizOrderService {
    Integer update(BizOrder bizOrder);

    Integer add(BizOrder bizOrder);

    IPage<BizOrder> selectByPage(Page<BizOrder> page, String userId, String state, String kw);

    Map<String, int[]> showOrder();
}
