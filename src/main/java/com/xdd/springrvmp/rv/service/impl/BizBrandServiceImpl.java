package com.xdd.springrvmp.rv.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.BizBrandDao;
import com.xdd.springrvmp.rv.model.BizBrand;
import com.xdd.springrvmp.rv.service.BizBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author AixLeft
 * Date 2021/3/18
 */
@Service
public class BizBrandServiceImpl implements BizBrandService {
    @Autowired
    private BizBrandDao bizBrandDao;

    @Override
    public IPage<BizBrand> selectByPage(Page<BizBrand> page, String kw) {
        return bizBrandDao.selectByPage(page, kw);
    }
}
