package com.xdd.springrvmp.rv.model.vo;

import com.xdd.springrvmp.rv.model.BizCar;
import com.xdd.springrvmp.rv.model.BizOrder;
import com.xdd.springrvmp.rv.model.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author AixLeft
 * Date 2021/1/20
 */
@Data
public class Order implements Serializable {
    private BizOrder order;
    private SysUser user;
    private BizCar car;
    private String day;

}
