package com.sun.health.springboot.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by 华硕 on 2018-05-15.
 */
@Component
//@Order(1)
public class CustomCommandLineRunner implements CommandLineRunner, Ordered {
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("自定义的CommandLineRunner调用");
        System.out.println(strings);
        System.out.println("---------------------------------");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
