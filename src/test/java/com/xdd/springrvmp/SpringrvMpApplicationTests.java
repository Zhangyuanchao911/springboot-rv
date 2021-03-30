package com.xdd.springrvmp;

import com.xdd.springrvmp.rv.dao.SysRoleDao;
import com.xdd.springrvmp.rv.model.SysRole;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringrvMpApplicationTests {

    private Logger logger = LoggerFactory.getLogger(SpringrvMpApplicationTests.class);
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void test() {
        redisTemplate.opsForValue().set("xdd", "Aixleft");
        System.out.println(redisTemplate.opsForValue().get("xdd"));

    }

}
