package com.sun.health.flowable.spring;

public class DIConstructorObj {

    private int num;

    private String str;

    private DIConstructorArgA argA;

    private DIConstructorArgB argB;

    public DIConstructorObj() {
    }

    public DIConstructorObj(int num, String str, DIConstructorArgA argA, DIConstructorArgB argB) {
        this.num = num;
        this.str = str;
        this.argA = argA;
        this.argB = argB;
    }

    public DIConstructorObj(DIConstructorArgA argA, DIConstructorArgB argB) {
        this.argA = argA;
        this.argB = argB;
    }

    public DIConstructorObj(int num, String str) {
        this.num = num;
        this.str = str;
    }

    public DIConstructorObj(int num, DIConstructorArgA argA) {
        this.num = num;
        this.argA = argA;
    }

    public DIConstructorObj(String str, DIConstructorArgB argB) {
        this.str = str;
        this.argB = argB;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public DIConstructorArgA getArgA() {
        return argA;
    }

    public void setArgA(DIConstructorArgA argA) {
        this.argA = argA;
    }

    public DIConstructorArgB getArgB() {
        return argB;
    }

    public void setArgB(DIConstructorArgB argB) {
        this.argB = argB;
    }
}
