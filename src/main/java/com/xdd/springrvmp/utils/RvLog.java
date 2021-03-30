package com.xdd.springrvmp.utils;

import java.lang.annotation.*;

/**
 * 自定义注解类
 * @Author AixLeft
 * Date 2021/2/1
 */
@Target(ElementType.METHOD) //注解放置的目标位置 -->method 方法上
@Retention(RetentionPolicy.RUNTIME) //注解在哪一个阶段执行
@Documented //生成文档
public @interface RvLog {

    String value() default "";
}
