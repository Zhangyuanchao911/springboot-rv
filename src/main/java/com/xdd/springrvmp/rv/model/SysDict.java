package com.xdd.springrvmp.rv.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_dict
 * @author 
 */
@Data
public class SysDict implements Serializable {
    /**
     * 字典表id
     */
    private String id;

    /**
     * 类型
     */
    private String typeId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 数值
     */
    private String value;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}