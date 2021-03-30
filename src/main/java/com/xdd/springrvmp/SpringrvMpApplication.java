package com.xdd.springrvmp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.xdd.springrvmp.rv.dao")

public class SpringrvMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringrvMpApplication.class, args);
    }

}
