package com.xdd.springrvmp.rv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.model.BizOrder;
import com.xdd.springrvmp.rv.model.vo.ShowOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BizOrderDao extends BaseMapper<BizOrder> {
    int deleteByPrimaryKey(String orderId);

    int insert(BizOrder record);

    int insertSelective(BizOrder record);

    BizOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(BizOrder record);

    int updateByPrimaryKey(BizOrder record);

    IPage<BizOrder> selectByPage(Page page,@Param("userId") String userId,@Param("state") String state,@Param("kw") String kw);

    List<ShowOrder> showOrder();
}