package com.xdd.springrvmp.rv.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_perm
 * @author 
 */
@Data
public class SysPerm implements Serializable {
    /**
     * 资源id
     */
    private String permId;

    /**
     * 资源名称
     */
    private String permName;

    /**
     * 权限字符串
     */
    private String permission;

    /**
     * 资源url
     */
    private String permUrl;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}