package com.sun.health.flowable.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 华硕 on 2018-04-28.
 */
public class TestDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context-test.xml");
        TestPointCutA pointCutA = (TestPointCutA) applicationContext.getBean("testPointCutA");
        pointCutA.funcA("Hello World!");
        System.out.println("******************************************");
        pointCutA.funcB("你好");
        System.out.println("******************************************");
        pointCutA.funcC("Spring");

    }

}
