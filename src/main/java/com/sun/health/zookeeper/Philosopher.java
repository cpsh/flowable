package com.sun.health.zookeeper;

import java.util.concurrent.TimeUnit;

/**
 * Created by 华硕 on 2018-07-03.
 */
public class Philosopher implements Runnable {

    private String name;

    private Chopstick left;

    private Chopstick right;

    private Long thinkTime;

    private Long eatTime;

    public Philosopher(String name, Chopstick left, Chopstick right, Long thinkTime, Long eatTime) {
        this.name = name;
        this.left = left;
        this.right = right;
        this.thinkTime = thinkTime;
        this.eatTime = eatTime;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                System.out.println(this + "开始思考");
                think();
                System.out.println(this + "停止思考");
                left.take();
                System.out.println(this + "获取左边筷子" + left);
                right.take();
                System.out.println(this + "获取右边筷子" + right);
                System.out.println(this + "开始就餐");
                eat();
                System.out.println(this + "就餐结束");
                left.drop();
                System.out.println(this + "归还左边筷子" + left);
                right.drop();
                System.out.println(this + "归还右边筷子" + right);
            }
            System.out.println(this + "线程阻塞");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void think() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(thinkTime);
    }

    public void eat() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(eatTime);
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "name='" + name + '\'' +
                '}';
    }
}
