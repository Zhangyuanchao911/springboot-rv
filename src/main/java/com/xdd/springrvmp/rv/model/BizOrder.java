package com.xdd.springrvmp.rv.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * biz_order
 *
 * @author
 */
@Data
public class BizOrder implements Serializable {
    /**
     * 订单押金
     */
    private String orderId;

    /**
     * 车辆id
     */
    private String carId;

    /**
     * 租金
     */
    private BigDecimal rental;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 租赁开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 租赁结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 客户电话
     */
    private String customerTel;

    /**
     * 订单创建员工
     */
    private String userId;

    /**
     * 订单状态
     */
    private String orderState;

    /**
     * 备注
     */
    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    public BizOrder() {
    }

    public BizOrder(String orderId, String carId, BigDecimal rental, BigDecimal deposit, Date startTime,
                    Date endTime, String customerName, String customerTel, String userId, String orderState,
                    String remark, Date createTime, Date updateTime, Boolean isDeleted) {
        this.orderId = orderId;
        this.carId = carId;
        this.rental = rental;
        this.deposit = deposit;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.customerTel = customerTel;
        this.userId = userId;
        this.orderState = orderState;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDeleted = isDeleted;
    }

    private static final long serialVersionUID = 1L;
}