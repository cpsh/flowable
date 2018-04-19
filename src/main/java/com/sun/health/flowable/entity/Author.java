package com.sun.health.flowable.entity;

import org.apache.ibatis.type.Alias;

/**
 * Created by 华硕 on 2018-04-19.
 */
@Alias("author")
public class Author {

    private int id;

    public Author() {

    }

    public Author(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
