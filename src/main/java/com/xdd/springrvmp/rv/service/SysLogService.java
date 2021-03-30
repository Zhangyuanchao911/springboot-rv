package com.xdd.springrvmp.rv.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.SysLog;
import com.xdd.springrvmp.rv.model.vo.Log;

import java.util.List;

/**
 * @Author AixLeft
 * Date 2021/2/1
 */
public interface SysLogService {
    Integer add(SysLog sysLog);

    void exportListExcel(List<Log> logList);

    IPage<SysLog> selectPage(Page<SysLog> page);

    IPage<SysLog> selectPageUserId(Page<SysLog> page, String userId);
}
