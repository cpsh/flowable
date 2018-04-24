package com.sun.health.flowable.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by 华硕 on 2018-04-24.
 */
public class MainApp {

    public static void main(String[] args) {

//        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("application-context.xml"));
//        HelloWorld helloWorld = (HelloWorld) beanFactory.getBean("helloWorld");
//        System.out.println(helloWorld.getMessage());

//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
//        HelloWorld helloWorld = (HelloWorld) applicationContext.getBean("helloWorld");
//        System.out.println(helloWorld.getMessage());
//        helloWorld.setMessage("不太好");
//        HelloWorld helloWorldB = (HelloWorld) applicationContext.getBean("helloWorld");
//        System.out.println(helloWorldB.getMessage());

//        applicationContext.registerShutdownHook();

        HelloChild helloChild = (HelloChild) applicationContext.getBean("helloChild");
        System.out.println(helloChild.getMessageA());
        System.out.println(helloChild.getMessageB());
        System.out.println(helloChild.getMessageC());

    }

}
