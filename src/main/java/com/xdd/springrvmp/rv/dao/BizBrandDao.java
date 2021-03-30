package com.xdd.springrvmp.rv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.BizBrand;
import org.apache.ibatis.annotations.Param;

public interface BizBrandDao extends BaseMapper<BizBrand> {
    int deleteByPrimaryKey(String brandId);

    int insert(BizBrand record);

    int insertSelective(BizBrand record);

    BizBrand selectByPrimaryKey(String brandId);

    int updateByPrimaryKeySelective(BizBrand record);

    int updateByPrimaryKey(BizBrand record);

    IPage<BizBrand> selectByPage(Page<BizBrand> page, @Param("kw") String kw);
}