package com.sun.health.flowable.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by 华硕 on 2018-04-28.
 */
@Aspect
public class CustomPointCut {

    @Pointcut("execution(* com.sun.health.flowable.spring.TestPointCutA.funcC(..))")
    private void getPointCut() {}

    @Before("getPointCut()")
    public void before() {
        System.out.println("============== before end ==========");
    }

    @After("getPointCut()")
    public void after() {
        System.out.println("============== after end ==========");
    }

    @AfterReturning(value = "getPointCut()", returning = "returnVal")
    public void afterReturning(Object returnVal) {
        System.out.println(returnVal);
        System.out.println("============ after returning end =============");
    }

    @AfterThrowing(value = "getPointCut()", throwing = "err")
    public void afterThrowing(Throwable err) {
        System.out.println(err);
        System.out.println("============= after throwing end ============");
    }

    @Around("getPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(joinPoint.getSignature());
        System.out.println(joinPoint.getArgs());
        System.out.println(joinPoint.getTarget());
        System.out.println("============== around ===================");
        Object result = joinPoint.proceed();
        System.out.println("=============== around ==================");
        return result;
    }

}
