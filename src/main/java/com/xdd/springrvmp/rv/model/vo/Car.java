package com.xdd.springrvmp.rv.model.vo;

import com.xdd.springrvmp.rv.model.BizBrand;
import com.xdd.springrvmp.rv.model.BizCar;
import com.xdd.springrvmp.rv.model.SysDict;
import lombok.Data;

import java.io.Serializable;

/** 车辆vo类
 * @Author AixLeft
 * Date 2021/1/20
 */
@Data
public class Car implements Serializable {
    private BizCar bizCar;
    private SysDict sysDictType;
    private SysDict sysDictGear;
    private BizBrand bizBrand;
}
