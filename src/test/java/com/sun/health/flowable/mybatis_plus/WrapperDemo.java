package com.sun.health.flowable.mybatis_plus;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.sun.health.flowable.mybatis_plus.model.Person;
import org.junit.Test;

import java.util.Date;

/**
 * Created by 华硕 on 2018-05-10.
 */
public class WrapperDemo {

    @Test
    public void testTSQL1() {
        EntityWrapper<Person> ew = new EntityWrapper<Person>();
        ew.setEntity(new Person());
        ew.where("user_name = {0}", "zhangsan").and("id = 1")
                .orNew("user_status = {0}", "0").or("status = 1")
                .notLike("user_nickname", "notvalue")
                .andNew("new = xx").like("hhh", "ddd")
                .andNew("pwd = 11").isNotNull("n2, n1").isNull("n3")
                .groupBy("x1").groupBy("x2, x3")
                .having("x1 = 11").having("x3 = 433")
                .orderBy("dd").orderBy("d1, d2");
        System.out.println(ew.getSqlSegment());
    }

    @Test
    public void testTSQL2() {
        Wrapper wrapper = Condition.create()
                .setSqlSelect("sum(quantity")
                .isNull("order_id")
                .eq("user_id", 1)
                .eq("type", 1)
                .in("status", new Integer[]{0, 1})
                .eq("product_id", 1)
                .between("create_time", new Date(), new Date())
                .eq("weal", 1);
        System.out.println(wrapper.getSqlSegment());

    }
}
