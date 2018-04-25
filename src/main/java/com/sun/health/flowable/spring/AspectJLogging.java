package com.sun.health.flowable.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class AspectJLogging {

    @Pointcut("execution(* com.sun.health.flowable.spring.Student.*(..))")
    private void selectAll() {}

    @Before("selectAll()")
    public void beforeAdvice() {
        System.out.println("before advice");
    }

    @After("selectAll()")
    public void afterAdvice() {
        System.out.println("after advice");
    }

    @AfterReturning(value = "selectAll()", returning = "returnVal")
    public void afterReturningAdvice(Object returnVal) {
        System.out.println("after returning advice: " + returnVal.toString());
    }

    @AfterThrowing(value = "selectAll()", throwing = "ex")
    public void afterThrowingAdvice(IllegalArgumentException ex) {
        System.out.println("after throwing advice: " + ex.toString());
    }

    @Around("selectAll()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around 前");
        Object result = joinPoint.proceed();
        System.out.println("around 后");
        return result;
    }

}
