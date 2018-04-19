package com.sun.health.flowable.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;
import java.util.concurrent.Executors;

/**
 * Created by 华硕 on 2018-04-19.
 */
@Intercepts({
    @Signature(
        type= Executor.class,
        method="update",
        args = {MappedStatement.class, Object.class}
    )
})
public class CustomPlugin implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    public Object plugin(Object o) {
        return null;
    }

    public void setProperties(Properties properties) {

    }
}
