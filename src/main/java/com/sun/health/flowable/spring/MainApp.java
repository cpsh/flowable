package com.sun.health.flowable.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
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

//        JavaCollection javaCollection = (JavaCollection) applicationContext.getBean("javaCollection");
//        System.out.println(javaCollection.getAddressList());
//        System.out.println(javaCollection.getAddressSet());
//        System.out.println(javaCollection.getAddressMap());
//        System.out.println(javaCollection.getAddressProps());

//        DIConstructorObj obj5 = (DIConstructorObj) applicationContext.getBean("obj5");
//        obj5.getArgA().funA();
//        obj5.getArgA().speak();
//        obj5.getArgB().funcB();
//        obj5.getArgB().speak();
//        System.out.println(obj5.getNum());
//        System.out.println(obj5.getStr());
        
//        DIConstructorObj obj6 = (DIConstructorObj) applicationContext.getBean("obj6");
//        obj6.getArgA().funA();
//        obj6.getArgA().speak();
//        obj6.getArgB().funcB();
//        obj6.getArgB().speak();
//        System.out.println(obj6.getNum());
//        System.out.println(obj6.getStr());

//        DIConstructorObj obj7 = (DIConstructorObj) applicationContext.getBean("obj7");
//        obj7.getArgA().funA();
//        obj7.getArgA().speak();
//        obj7.getArgB().funcB();
//        obj7.getArgB().speak();
//        System.out.println(obj7.getNum());
//        System.out.println(obj7.getStr());

//        DIConstructorObj obj8 = applicationContext.getBean(DIConstructorObj.class);
//        System.out.println(obj8.getArgA());
//        System.out.println(obj8.getArgB());
//        System.out.println(obj8.getNum());
//        System.out.println(obj8.getStr());

//        DIConstructorObj obj8 = (DIConstructorObj) applicationContext.getBean("obj8");
//        System.out.println(obj8.getArgA());
//        System.out.println(obj8.getArgB());
//        System.out.println(obj8.getNum());
//        System.out.println(obj8.getStr());
//        System.out.println(obj8.getNoSetter());

//        Profile profile = (Profile) applicationContext.getBean("profile");
//        profile.printAge();
//        profile.printName();

//        ApplicationContext annotationApplicationContext = new AnnotationConfigApplicationContext(HelloWorldConfig.class);
//        HelloWorld helloWorld = (HelloWorld) annotationApplicationContext.getBean("helloWorld");
//        System.out.println(helloWorld.getMessage());

        ApplicationContext annotationApplicationContext = new AnnotationConfigApplicationContext(TextEditorConfig.class);
        TextEditor textEditor = (TextEditor) annotationApplicationContext.getBean("textEditor");
        textEditor.spellCheck();

        CustomEventPublisher publisher = (CustomEventPublisher) annotationApplicationContext.getBean("customEventPublisher");
        publisher.publish(null);
        publisher.publish(null);


    }

}
