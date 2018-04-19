package com.sun.health.flowable.entity.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * Created by 华硕 on 2018-04-19.
 */
public interface AuthorDao {

    HashMap<String, Object> selectAuthor(@Param("id")int id);

}
