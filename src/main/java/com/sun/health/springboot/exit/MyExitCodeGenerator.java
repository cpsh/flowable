package com.sun.health.springboot.exit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * Created by 华硕 on 2018-05-16.
 */
@Component
public class MyExitCodeGenerator implements ExitCodeGenerator, DisposableBean {

    @Override
    public int getExitCode() {

        return 0;
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("PreDestroy:" + new Date());
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("应用结束: " + new Date());
    }
}
