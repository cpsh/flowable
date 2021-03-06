package com.sun.health.springboot;

import com.sun.health.springboot.bean.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by 华硕 on 2018-05-14.
 */
@RestController
//@EnableAutoConfiguration
@SpringBootApplication
public class Example {

    @Autowired
    private MyBean myBean;

    @Autowired
    public Example(ApplicationArguments args) {
        boolean debug = args.containsOption("debug");
        List<String> files = args.getNonOptionArgs();
        for (String s : args.getOptionNames()) {
            System.out.println(s);
        }
        System.out.println("==========================");
        for (String s : args.getNonOptionArgs()) {
            System.out.println(s);
        }
    }

    @RequestMapping("/")
    String home() {
        System.out.println(myBean.getGame());
        System.out.println(myBean.getPf());
        return "Hello World!" + myBean.getName() + myBean.getA()  + myBean.getB()  + myBean.getC()  + myBean.getD();
    }

    public static void main(String[] args) {
        SpringApplication.run(Example.class);
    }


}
