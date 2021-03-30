package com.xdd.springrvmp.rv.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.BizCar;
import com.xdd.springrvmp.rv.model.vo.Car;
import com.xdd.springrvmp.rv.model.vo.ShowCar;

import java.util.List;

/**
 * @Author AixLeft
 * Date 2021/1/18
 */

public interface BizCarService {
    Integer addSelected(BizCar bizCar);

    Integer deleteByPrimaryKey(String id);

    BizCar selectById(String id);

    void exportListExcel(List<Car> carLists);

    List<BizCar> selectAll();

    IPage<BizCar> selectPage(Page<BizCar> page,String kw, String state);

    /**
     * 下列为租赁分页方法
     *
     * @param page
     * @return
     */
    IPage<BizCar> selectPageSave(Page<BizCar> page,String kw ,String type, String gear);


    List<ShowCar> selectShowCar();
}
