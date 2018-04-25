package com.sun.health.flowable.spring;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class CustomAdviceHandler implements MethodBeforeAdvice, AfterReturningAdvice {

    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("adviceHandler 执行前");
    }

    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("adviceHandler 执行后");
    }
}
