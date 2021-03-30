package com.xdd.springrvmp.rv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.BizCar;
import com.xdd.springrvmp.rv.model.vo.ShowCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BizCarDao extends BaseMapper<BizCar> {
    int deleteByPrimaryKey(String carId);

    int insert(BizCar record);

    int insertSelective(BizCar record);

    BizCar selectByPrimaryKey(String carId);

    int updateByPrimaryKeySelective(BizCar record);

    int updateByPrimaryKey(BizCar record);

    int updateStateById(String carId, String state);

    IPage<BizCar> selectByPage(Page page, @Param(value = "kw") String kw, @Param(value = "state") String state);

    List<BizCar> selectAll();

    /**
     * 下面为租赁时分页方法
     */
    IPage<BizCar> selectByPageSave(Page page, @Param(value = "kw") String kw, @Param(value = "type") String type, @Param(value = "gear") String gear);


    /**
     * echars 显示数据查询房车类型数量
     */
    List<ShowCar> selectShowCar();
}