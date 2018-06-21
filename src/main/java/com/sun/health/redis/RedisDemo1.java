package com.sun.health.redis;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * Created by 华硕 on 2018-06-21.
 */
@SpringBootTest(classes = {RedisDemo1.class})
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class RedisDemo1 {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    @Profile("dev")
    public void testRedisTemplate() {

        Set keys = stringRedisTemplate.keys("*");
        for (Object key : keys) {
            System.out.println(String.valueOf(key));
        }

        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String name = stringStringValueOperations.get("name");
        System.out.println(name);

        SetOperations<String, String> stringStringSetOperations = stringRedisTemplate.opsForSet();
        Set<String> set1 = stringStringSetOperations.members("set1");
        for (String s : set1) {
            System.out.println(s);
        }

    }

}
