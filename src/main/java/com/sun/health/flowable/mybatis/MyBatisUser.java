package com.sun.health.flowable.mybatis;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by 华硕 on 2018-04-23.
 */
@Alias("User")
public class MyBatisUser implements Serializable {

    private static final long serialVersionUID = 7998383381425842435L;

    private Long id;

    private String name;

    private int age;

    public MyBatisUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyBatisUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
