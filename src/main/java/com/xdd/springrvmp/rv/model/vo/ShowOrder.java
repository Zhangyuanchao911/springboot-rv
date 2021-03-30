package com.xdd.springrvmp.rv.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author AixLeft
 * Date 2021/3/21
 */

@Data
public class ShowOrder implements Serializable {
    private String rental;
    private String month;
    private String brandName;
}
