package com.sun.health.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 华硕 on 2018-07-03.
 */
public class ZKMatch {

    private static ZooKeeper zooKeeper;

    private static ZookeeperConnection connection;

    private static CountDownLatch countDownLatch;

    public static void match(String path, Watcher watcher, Stat stat) throws KeeperException, InterruptedException {
        zooKeeper.getData(path, watcher, stat);
    }

    public static void main(String[] args) throws KeeperException {
        String path = "/zkFromJava";

        try {
            countDownLatch = new CountDownLatch(1);
            connection = new ZookeeperConnection();
            zooKeeper = connection.connect("47.105.97.246:2181");
            match(path, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                        System.out.println("数据更改");
                        countDownLatch.countDown();
                    }
                }
            }, null);
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
