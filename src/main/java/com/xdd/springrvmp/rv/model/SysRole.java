package com.xdd.springrvmp.rv.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_role
 * @author 
 */
@Data
public class SysRole implements Serializable {
    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色
     */
    private String role;

    /**
     * 角色名称
     */
    private String roleName;

    private Date creatTime;

    private Date updateTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}