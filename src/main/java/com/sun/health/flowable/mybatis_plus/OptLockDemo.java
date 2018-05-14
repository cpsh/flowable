package com.sun.health.flowable.mybatis_plus;

import com.sun.health.flowable.mybatis_plus.dao.PPersonMapper;
import com.sun.health.flowable.mybatis_plus.model.PPerson;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 华硕 on 2018-05-14.
 */
public class OptLockDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-mybatis-plus.xml");
        PPersonMapper pPersonMapper = applicationContext.getBean(PPersonMapper.class);
        PPerson pPerson = pPersonMapper.selectById(1L);
        pPerson.setpAge(11);
        pPersonMapper.updateById(pPerson);
    }

}
