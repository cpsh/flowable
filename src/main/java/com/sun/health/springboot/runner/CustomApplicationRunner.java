package com.sun.health.springboot.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by 华硕 on 2018-05-15.
 */
@Component
//@Order(2)
public class CustomApplicationRunner implements ApplicationRunner, Ordered {
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("自定义的ApplicationRunner调用");
        System.out.println(applicationArguments.getOptionNames());
        System.out.println("***********************************");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
