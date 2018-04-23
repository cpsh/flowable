package com.sun.health.flowable.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by 华硕 on 2018-04-23.
 */
public class Demo {

    public static void main(String[] args) {
        try {
            SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-configuration.xml")).openSession();
            UserDao userDao = sqlSession.getMapper(UserDao.class);
//            MyBatisUser myBatisUser = new MyBatisUser();
//            myBatisUser.setName("SJ");
//            myBatisUser.setAge(25);
//            userDao.addUser(myBatisUser);
//            sqlSession.commit();
            MyBatisUser user = userDao.findById(2L);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
