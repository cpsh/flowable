package com.sun.health.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by 华硕 on 2018-07-03.
 */
public class ZKCreate {

    private static ZooKeeper zooKeeper;

    private static ZookeeperConnection connection;

    public static void create(String path, byte[] data) throws IOException, InterruptedException, KeeperException {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public static void main(String[] args) {
        String path = "/zkFromJava";
        byte[] data = "这是从Java生成的节点".getBytes();

        try {
            connection = new ZookeeperConnection();
            zooKeeper = connection.connect("47.105.97.246:2181");
            create(path, data);
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

}
