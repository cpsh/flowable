package com.sun.health.flowable.spring.transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by 华硕 on 2018-04-28.
 */
public class TestTransaction {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context-transaction.xml");
        StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate) applicationContext.getBean("studentJDBCTemplate");
        studentJDBCTemplate.create("S1", 11, 12, 13);
        studentJDBCTemplate.create("S2", 21, 22, 23);
        studentJDBCTemplate.create("S3", 31, 32, 33);
        studentJDBCTemplate.create("S4", 41, 42, 43);
        List<StudentMarks> studentMarks = studentJDBCTemplate.listStudents();
        for (StudentMarks studentMark : studentMarks) {
            System.out.println(studentMark.getId());
            System.out.println(studentMark.getName());
            System.out.println(studentMark.getAge());
            System.out.println(studentMark.getMarks());
            System.out.println(studentMark.getYear());
            System.out.println(studentMark.getSid());
            System.out.println("======================");
        }
    }

}
