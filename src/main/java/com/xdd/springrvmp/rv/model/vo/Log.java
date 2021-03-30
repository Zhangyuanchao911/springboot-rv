package com.xdd.springrvmp.rv.model.vo;

import com.xdd.springrvmp.rv.model.SysLog;
import com.xdd.springrvmp.rv.model.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author AixLeft
 * Date 2021/2/2
 */
@Data
public class Log implements Serializable {
    private SysLog sysLog;
    private SysUser sysUser;
}
