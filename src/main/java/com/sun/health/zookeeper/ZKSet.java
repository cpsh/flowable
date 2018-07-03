package com.sun.health.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by 华硕 on 2018-07-03.
 */
public class ZKSet {

    private static ZooKeeper zooKeeper;

    private static ZookeeperConnection connection;

    public static Stat set(String path, byte[] data) throws KeeperException, InterruptedException {
        Stat result = zooKeeper.setData(path, data, 0);
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        String path = "/zkFromJava";
        byte[] data = "这是从Java更新的数据".getBytes();
        try {
            connection = new ZookeeperConnection();
            zooKeeper = connection.connect("47.105.97.246:2181");
            set(path, data);
//            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
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
