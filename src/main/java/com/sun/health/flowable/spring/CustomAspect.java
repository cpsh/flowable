package com.sun.health.flowable.spring;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * Created by 华硕 on 2018-04-28.
 */
public class CustomAspect {

    public void before() {
//        System.out.println(method);
//        System.out.println(args);
//        System.out.println(target);
        System.out.println("============== before end ================");
    }

    public void after() {
//        System.out.println(method);
//        System.out.println(args);
//        System.out.println(target);
        System.out.println("============== after end ================");
    }

    public void afterReturning(Object returnVal) {
//        System.out.println(method);
//        System.out.println(args);
//        System.out.println(target);
        System.out.println(returnVal);
        System.out.println("============== after returning end ================");
    }

    public void afterThrowing(Throwable err) {
//        System.out.println(method);
//        System.out.println(args);
//        System.out.println(target);
        System.out.println(err);
        System.out.println("============== after throwing end ================");
    }

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println(method);
//        System.out.println(args);
//        System.out.println(target);
        System.out.println("============== around ================");
        Object result = joinPoint.proceed();
        System.out.println("============== around ===============");
        return result;
    }

}
