package com.xdd.springrvmp.rv.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * biz_car
 * @author 
 */
@Data
public class BizCar implements Serializable {
    /**
     * 汽车id
     */
    private String carId;

    /**
     * 汽车名称
     */
    private String carName;

    /**
     * 每日租赁价格
     */
    private BigDecimal carPrice;

    /**
     * 押金
     */
    private BigDecimal carDeposit;

    /**
     * 车牌号
     */
    private String carNumber;

    private String brandId;

    /**
     * 房车类型
     */
    private String type;

    /**
     * 档类
     */
    private String gear;

    /**
     * 车辆描述
     */
    private String carDescribe;

    private Date creatTime;

    private Date updateTime;

    /**
     * 汽车租赁状态
     */
    private String state;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}