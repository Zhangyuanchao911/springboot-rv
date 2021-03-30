package com.xdd.springrvmp.rv.model.vo;

import com.xdd.springrvmp.rv.model.SysRole;
import com.xdd.springrvmp.rv.model.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author AixLeft
 * Date 2021/1/20
 */
@Data
public class User implements Serializable {
    private SysUser user;
    private SysRole role;
}
