package com.xdd.springrvmp.utils;

import java.util.UUID;

/**
 * 生成32位UUID工具类
 *
 * @Author AixLeft
 * Date 2021/1/18
 */

public class UUIDUtils {
    public UUIDUtils(){
    }
    public static String getUUId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
