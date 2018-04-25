package com.sun.health.flowable.spring;

import org.aspectj.lang.ProceedingJoinPoint;

public class Logging {

    public void beforeAdvice() {
        System.out.println("Going to setup student profile");
    }

    public void afterAdvice() {
        System.out.println("Student profile has been setup");
    }

    public void afterReturningAdvice(Object returnVal) {
        System.out.println("Returning: " + (returnVal == null ? null : returnVal.toString()));
    }

    public void afterThrowingAdvice(IllegalArgumentException ex) {
        System.out.println("There has been an exception: " + ex.toString());
    }

    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around advice");
        System.out.println(joinPoint.getArgs());
        System.out.println("执行前");
        Object result = joinPoint.proceed();
        System.out.println("执行后");
        return result;
    }

}
