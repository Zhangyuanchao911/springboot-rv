package com.xdd.springrvmp.rv.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_user
 * @author 
 */
@Data
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 电话
     */
    private String userTel;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 角色id
     */
    private String roleId;

    private Date creatTime;

    private Date updateTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}