package com.xdd.springrvmp.rv.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author AixLeft
 * Date 2021/3/22
 */
@Data
public class ShowOrderData implements Serializable {
    private String name;
    private String type;
    private String stack;
    private int[] data;
}
