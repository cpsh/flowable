package com.sun.health.springboot.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华硕 on 2018-05-16.
 */
@Component
@ConfigurationProperties(prefix = "")
public class MyBean {

//    @Value("${name}")
    private String name;

//    @Value("${a}")
    private String a;

//    @Value("${b}")
    private String b;

//    @Value("${c}")
    private String c;

//    @Value("${d}")
    private String d;

//    @Value("${game}")
    private List<String> game = new ArrayList<String>();

    private String pf;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public List<String> getGame() {
        return game;
    }

    public void setGame(List<String> game) {
        this.game = game;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }
}


