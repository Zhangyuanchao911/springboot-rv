package com.xdd.springrvmp.rv.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_log
 * @author 
 */
@Data
public class SysLog implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 操作
     */
    private String operation;

    /**
     * 参数
     */
    private String params;

    /**
     * 方法名
     */
    private String method;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 操作时间
     */
    private Date creatTime;

    private static final long serialVersionUID = 1L;
}