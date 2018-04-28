package com.sun.health.flowable.spring;

import org.aopalliance.aop.Advice;
import org.springframework.aop.*;

import java.lang.reflect.Method;

/**
 * Created by 华硕 on 2018-04-28.
 */
public class CustomAdvisor implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {

        System.out.println(o);
        System.out.println(method);
        System.out.println(objects);
        System.out.println(o1);
        System.out.println("=================== afterReturning end ==================");

    }

    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println(method);
        System.out.println(objects);
        System.out.println(o);
        System.out.println("==================== before =============================");
    }

    public void afterThrowing(Method method, Object[] args, Object target, Throwable err) {
        System.out.println(method);
        System.out.println(args);
        System.out.println(target);
        System.out.println(err);
    }

}
