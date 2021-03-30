package com.xdd.springrvmp.rv.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * biz_brand
 * @author 
 */
@Data
public class BizBrand implements Serializable {
    /**
     * 汽车品牌id
     */
    private String brandId;

    /**
     * 汽车品牌名称
     */
    private String brandName;

    private Date creatTime;

    private Date updateTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}