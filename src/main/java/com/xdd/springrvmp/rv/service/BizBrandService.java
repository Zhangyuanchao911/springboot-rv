package com.xdd.springrvmp.rv.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.BizBrand;

/**
 * @Author AixLeft
 * Date 2021/3/18
 */
public interface BizBrandService {
    IPage<BizBrand> selectByPage(Page<BizBrand> page, String kw);
}
