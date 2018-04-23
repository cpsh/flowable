package com.sun.health.flowable.mybatis;

import org.apache.ibatis.annotations.Param;

/**
 * Created by 华硕 on 2018-04-23.
 */
public interface UserDao {

    int addUser(MyBatisUser user);

    MyBatisUser findById(@Param("id") Long id);

}
