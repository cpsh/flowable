package com.sun.health.zookeeper;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by 华硕 on 2018-07-03.
 */
public class DeadLockDemo1 {


    @Test
    public void test1() throws InterruptedException {
        char c = 'A';
        Chopstick[] chopsticks = new Chopstick[5];
        for (int i = 0; i < 5; ) {
            chopsticks[i] = new Chopstick(++i);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5;) {
            executorService.execute(new Philosopher(String.valueOf((char)(c + i)), chopsticks[i++ % 5], chopsticks[i % 5], 1L, 1L));
        }
        TimeUnit.SECONDS.sleep(10);
        executorService.shutdown();
    }

}
