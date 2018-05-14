package com.sun.health.flowable.mybatis_plus;

import com.sun.health.flowable.mybatis_plus.dao.PPersonMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 华硕 on 2018-05-14.
 */
public class SqlInjectorDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-mybatis-plus.xml");
        PPersonMapper pPersonMapper = applicationContext.getBean(PPersonMapper.class);
        pPersonMapper.deleteAll();
    }

}
