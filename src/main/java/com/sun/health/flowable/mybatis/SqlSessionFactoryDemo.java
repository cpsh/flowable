package com.sun.health.flowable.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Created by 华硕 on 2018-04-20.
 */
public class SqlSessionFactoryDemo {

    /**
     *
     * 主要Java接口是SqlSession，可以通过这个接口执行命令，获取映射器和管理事务。
     * SqlSession由SqlSessionFactory创建
     * SqlSessionFactory由SqlSessionFactoryBuilder创建
     * 一般由框架注入SqlSessionFactory，无需了解细节
     *
     * @param args
     */
    public static void main(String[] args) {

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(SqlSessionFactoryDemo.class.getResourceAsStream("mybatis-configuration.xml"));


    }

}
