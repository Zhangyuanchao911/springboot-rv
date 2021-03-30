package com.xdd.springrvmp.rv.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_role_perm
 * @author 
 */
@Data
public class SysRolePerm implements Serializable {
    /**
     * 角色资源表id
     */
    private String sysRolePermId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 资源id
     */
    private String permId;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}