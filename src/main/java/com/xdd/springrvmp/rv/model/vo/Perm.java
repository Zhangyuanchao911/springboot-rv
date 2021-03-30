package com.xdd.springrvmp.rv.model.vo;

import com.xdd.springrvmp.rv.model.SysPerm;
import com.xdd.springrvmp.rv.model.SysRolePerm;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author AixLeft
 * Date 2021/2/3
 */
@Data
public class Perm implements Serializable {
    private SysRolePerm sysRolePerm;
    private SysPerm sysPerm;
}
