package com.sun.health.flowable.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * Created by 华硕 on 2018-04-24.
 */
public class ABeanPostProcessor implements BeanPostProcessor, Ordered {

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("Before A" + o);
        System.out.println("Before A" + s);
        if(o instanceof HelloWorld) {
            System.out.println("A BeanPostProcessor Before Initialization");
        }
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("After A" + o);
        System.out.println("After A" + s);
        if(o instanceof HelloWorld) {
            System.out.println("A BeanPostProcessor After Initialization");
        }
        return o;
    }

    public int getOrder() {
        return 2;
    }
}
