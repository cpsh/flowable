package com.sun.health.zookeeper;

/**
 * Created by 华硕 on 2018-07-03.
 */
public class Chopstick {

    private int index;

    private boolean isTaken = false;

    public Chopstick() {
    }

    public Chopstick(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Chopstick{" +
                "index=" + index +
                ", isTaken=" + isTaken +
                '}';
    }

    public synchronized void take() throws InterruptedException {
        while(isTaken)
            wait();
        isTaken = true;
    }

    public synchronized void drop() {
        isTaken = false;
        notifyAll();
    }
}
