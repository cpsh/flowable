package com.sun.health.flowable.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 华硕 on 2018-04-25.
 */
public class CustomApplicationListener implements ApplicationListener<CustomEvent> {


    public void onApplicationEvent(CustomEvent customEvent) {
        System.out.println("接受自定义事件");
    }
}
