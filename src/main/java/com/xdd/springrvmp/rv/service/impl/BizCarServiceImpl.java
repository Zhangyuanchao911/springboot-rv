package com.xdd.springrvmp.rv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.BizCarDao;
import com.xdd.springrvmp.rv.model.BizCar;
import com.xdd.springrvmp.rv.model.vo.Car;
import com.xdd.springrvmp.rv.model.vo.ShowCar;
import com.xdd.springrvmp.rv.service.BizCarService;
import com.xdd.springrvmp.utils.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author AixLeft
 * Date 2021/1/18
 */
@Service
public class BizCarServiceImpl implements BizCarService {
    @Autowired
    private BizCarDao bizCarDao;

    @Override
    public Integer addSelected(BizCar bizCar) {
        return bizCarDao.insertSelective(bizCar);
    }

    @Override
    public Integer deleteByPrimaryKey(String id) {
        return bizCarDao.deleteByPrimaryKey(id);
    }


    @Override
    public BizCar selectById(String id) {
        return bizCarDao.selectByPrimaryKey(id);
    }


    @Override
    public void exportListExcel(List<Car> carList) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < carList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("index", i + 1 + "");
            map.put("carName", carList.get(i).getBizCar().getCarName());
            map.put("carNumber", carList.get(i).getBizCar().getCarNumber());
            map.put("carPrice", carList.get(i).getBizCar().getCarPrice());
            map.put("brandName", carList.get(i).getBizBrand().getBrandName());
            map.put("type", carList.get(i).getSysDictType().getValue());
            map.put("gear", carList.get(i).getSysDictGear().getValue());
            map.put("carDescribe", carList.get(i).getBizCar().getCarDescribe());
            Date time = carList.get(i).getBizCar().getCreatTime();
            //日期格式化  yyyy-MM-dd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String creatTime = sdf.format(time);
            map.put("creatTime", creatTime);
            map.put("state", carList.get(i).getBizCar().getState());
            mapList.add(map);

        }
        // 定义模板数据集合
        Map<String, Object> dataMap = new HashMap<>(8);
        // 放入数据
        dataMap.put("list", mapList);
        // 执行导出并下载
        ExportExcel.downloadExport("excelTemplate/biz_car.xlsx", "车辆信息.xlsx", dataMap);
    }

    @Override
    public List<BizCar> selectAll() {
        return bizCarDao.selectAll();
    }

    @Override
    public IPage<BizCar> selectPage(Page<BizCar> page, String kw, String state) {
        return bizCarDao.selectByPage(page, kw, state);
    }


    @Override
    public IPage<BizCar> selectPageSave(Page<BizCar> page, String kw, String type, String gear) {
        return bizCarDao.selectByPageSave(page, kw, type, gear);
    }


    @Override
    public List<ShowCar> selectShowCar() {
        return bizCarDao.selectShowCar();
    }


}