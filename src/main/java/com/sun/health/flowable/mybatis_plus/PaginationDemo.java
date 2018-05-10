package com.sun.health.flowable.mybatis_plus;

import com.baomidou.mybatisplus.plugins.Page;
import com.sun.health.flowable.mybatis_plus.dao.PPersonMapper;
import com.sun.health.flowable.mybatis_plus.model.PPerson;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by 华硕 on 2018-05-10.
 */
public class PaginationDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-mybatis-plus.xml");
        PPersonMapper mapper = applicationContext.getBean(PPersonMapper.class);

        Page<PPerson> page = new Page<PPerson>(0, 10);
        List<PPerson> pPeople = mapper.selectPPersonList(page);
        System.out.println(pPeople);
    }

}
