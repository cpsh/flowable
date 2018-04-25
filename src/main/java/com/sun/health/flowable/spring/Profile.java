package com.sun.health.flowable.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by 华硕 on 2018-04-25.
 */
public class Profile {

    @Autowired
    @Qualifier("student2")
    private Student student;

    public Profile() {
    }

    public Student getStudent() {
        return student;
    }

    public void printAge() {
        System.out.println("age" + this.student.getAge());
    }

    public void printName() {
        System.out.println("name:" + this.student.getName());
    }
}
