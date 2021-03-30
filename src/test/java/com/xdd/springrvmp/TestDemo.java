package com.xdd.springrvmp;

import com.xdd.springrvmp.utils.ImportExcel;
import com.xdd.springrvmp.utils.UUIDUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author AixLeft
 * Date 2021/1/13
 */

public class TestDemo {
    @Test
    void t() {
        String deptId = "0000101-0001";
        if (deptId.endsWith("-000")) {
            deptId = deptId.substring(0, deptId.length() - 4);
        }
        System.out.println(deptId);
    }

    @Test
    void test() {
        Map<Integer, String> map = new HashMap<>(6);
        map.put(1, "a");
        map.put(5, "aa");
        map.put(3, "bf");
        map.put(7, "add");
        map.put(10, "baa");
        System.out.println(map.toString());
        map.forEach((key, value) -> {
            System.out.println(key + "----" + value);
        });

        System.out.println("-----------------------");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(3);
        list.add(7);
        list.add(10);
        list.forEach((integer -> {
            System.out.println(integer);
        }));
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }

        Collections.sort(list);

        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        list.forEach((integer -> {
            System.out.println(integer);
        }));
    }

    @Test
    void test01() {
        Date date = new Date();
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        System.out.println("请输入您的出生年月日（格式为：年-月-日）：");
        String time = sc.next();
        try {
            Date d = sdf.parse(time); // 将日期和时间的字符串表示形式转换为其等效的 DateTime。
            // date.getTime() 返回时间的毫秒数值
            int year = (int) ((date.getTime() - d.getTime()) / (1000 * 60 * 60 * 24)) / 365; // 计算年
            int day = (int) ((date.getTime() - d.getTime()) / (1000 * 60 * 60 * 24)); // 计算天
            System.out.println("您距今已生活了" + year + "年，" + "总共" + day + "天。");

        } catch (ParseException e) {
            System.out.println("输入日期格式不对！！！");
        }
        sc.close();
    }

    @Test
    void testExcel() throws IOException {
        InputStream is = new FileInputStream(new File("C:/Users/91145/Desktop/导出.xls"));
        List<List<String>> lists = ImportExcel.readExcel(is);
        for (List<String> list : lists
        ) {
            System.out.println(list);
        }
    }

    @Test
    void test11() {
        System.out.println("dd");
        System.out.println("\"d\" = " + "d");
        String[] s = new String[20];
        for (int i = 0; i < s.length; i++) {

            new TestDemo();

        }
    }

    @Test
    void test12() {
        Object salt = ByteSource.Util.bytes("111");
        String newPassword = new SimpleHash("MD5", "111", salt, 1024).toHex();
        System.out.println(newPassword);
    }

    @Test
    void test111() {
        System.out.println(UUIDUtils.getUUId());
    }
}
