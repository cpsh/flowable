package com.sun.health.websocket;

/**
 * Created by 华硕 on 2018-06-12.
 */
public class UserBean {

    private Integer id;

    private String name;

    private String pwd;

    public UserBean() {
    }

    public UserBean(Integer id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
