package com.sun.health.flowable.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by 华硕 on 2018-04-24.
 */
public class HelloWorld implements InitializingBean, DisposableBean {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void init_method() {
        System.out.println("init-method");
    }

    public void destroy_method() {
        System.out.println("destroy-method");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean afterPropertiesSet");
    }

    public void destroy() throws Exception {
        System.out.println("DisposableBean destroy");
    }

    @PreDestroy
    public void pre_destroy() {
        System.out.println("pre_destroy");
    }

    @PostConstruct
    public void post_construct() {
        System.out.println("post_construct");
    }
}
