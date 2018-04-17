package com.sun.health.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * Created by 华硕 on 2018-04-17.
 */
public class CallExternalSystemDelegate implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) {
//        System.out.println("暂空");
        System.out.println("为员工:" + delegateExecution.getVariable("employee") + "调用外部系统");
    }

}
