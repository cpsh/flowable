package com.sun.health.flowable.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * Created by 华硕 on 2018-04-24.
 */
public class BBeanPostProcessor implements BeanPostProcessor, Ordered {

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("Before B" + o);
        System.out.println("Before B" + s);
        if(o instanceof HelloWorld) {
            System.out.println("B BeanPostProcessor Before Initialization");
        }
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("After B" + o);
        System.out.println("After B" + s);
        if(o instanceof HelloWorld) {
            System.out.println("B BeanPostProcessor Before Initialization");
        }
        return o;
    }

    public int getOrder() {
        return 1;
    }
}
