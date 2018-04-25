package com.sun.health.flowable.spring;

import org.springframework.context.ApplicationEvent;

/**
 * Created by 华硕 on 2018-04-25.
 */
public class CustomEvent extends ApplicationEvent {

    public CustomEvent(Object source) {
        super(source);
    }


}
