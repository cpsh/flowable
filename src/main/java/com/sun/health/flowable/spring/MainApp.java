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

//        HelloChild helloChild = (HelloChild) applicationContext.getBean("helloChild");
//        System.out.println(helloChild.getMessageA());
//        System.out.println(helloChild.getMessageB());
//        System.out.println(helloChild.getMessageC());

//        DIConstructorObj obj = (DIConstructorObj) applicationContext.getBean("obj");
//        obj.getArgA().funA();
//        obj.getArgA().speak();
//        obj.getArgB().funcB();
//        obj.getArgB().speak();
//
//        DIConstructorObj obj2 = (DIConstructorObj) applicationContext.getBean("obj2");
//        System.out.println(obj2.getNum());
//        System.out.println(obj2.getStr());
//
//        DIConstructorObj obj3 = (DIConstructorObj) applicationContext.getBean("obj3");
//        System.out.println(obj3.getNum());
//        System.out.println(obj3.getStr());
//
//        DIConstructorObj obj4 = (DIConstructorObj) applicationContext.getBean("obj4");
//        obj4.getArgA().funA();
//        obj4.getArgA().speak();
//        obj4.getArgB().funcB();
//        obj4.getArgB().speak();

        JavaCollection javaCollection = (JavaCollection) applicationContext.getBean("javaCollection");
        System.out.println(javaCollection.getAddressList());
        System.out.println(javaCollection.getAddressSet());
        System.out.println(javaCollection.getAddressMap());
        System.out.println(javaCollection.getAddressProps());

    }

}
