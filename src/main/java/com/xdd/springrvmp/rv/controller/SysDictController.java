package com.xdd.springrvmp.rv.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xdd.springrvmp.rv.dao.SysDictDao;
import com.xdd.springrvmp.rv.model.SysDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author AixLeft
 * @since 2021-01-18
 */
@RestController
@RequestMapping("/rv/sys-dict")
public class SysDictController {
    @Autowired
    private SysDictDao sysDictDao;

    /**
     * 房车类型 字典
     * @return
     */
    @RequestMapping("/type")
    public List<SysDict> getType(){
        return sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id","type"));
    }

    /**
     * 档类 字典
     * @return
     */
    @RequestMapping("/gear")
    public List<SysDict> getGear(){
        return sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id","gear"));
    }

    @RequestMapping("/sex")
    public List<SysDict> getSex(){
        return sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id","sex"));
    }

}
