package com.xdd.springrvmp.rv.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.SysLogDao;
import com.xdd.springrvmp.rv.model.SysLog;
import com.xdd.springrvmp.rv.model.vo.Log;
import com.xdd.springrvmp.rv.service.SysLogService;
import com.xdd.springrvmp.utils.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author AixLeft
 * Date 2021/2/1
 */
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public Integer add(SysLog sysLog) {
        return sysLogDao.insert(sysLog);
    }

    @Override
    public void exportListExcel(List<Log> logList) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < logList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("index", i + 1 + "");
            map.put("userName", logList.get(i).getSysUser().getUserName());
            map.put("operation", logList.get(i).getSysLog().getOperation());
            map.put("params", logList.get(i).getSysLog().getParams());
            map.put("method", logList.get(i).getSysLog().getMethod());

            //日期格式化  yyyy-MM-dd
            Date time = logList.get(i).getSysLog().getCreatTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String creatTime = sdf.format(time);
            map.put("creatTime", creatTime);
            mapList.add(map);
        }
        // 定义模板数据集合
        Map<String, Object> dataMap = new HashMap<>(16);
        // 放入数据
        dataMap.put("list", mapList);
        // 执行导出并下载
        ExportExcel.downloadExport("excelTemplate/biz_log.xlsx", "订单日志信息.xlsx", dataMap);
    }

    @Override
    public IPage<SysLog> selectPage(Page<SysLog> page) {
        return sysLogDao.selectByPage(page);
    }

    @Override
    public IPage<SysLog> selectPageUserId(Page<SysLog> page, String userId) {
        return sysLogDao.selectByPageUserId(page, userId);
    }
}
