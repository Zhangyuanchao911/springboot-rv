package com.xdd.springrvmp.rv.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author AixLeft
 * Date 2021/3/20
 */
@Data
public class ShowCar implements Serializable {
    /**
     * 房车类型总数
     */
    private String value;

    /**
     * 房车类型名
     */
    private String name;
}
