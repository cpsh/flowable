package com.sun.health.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by 华硕 on 2018-07-03.
 */
public class ZKExists {

    private static ZooKeeper zooKeeper;

    private static ZookeeperConnection connection;

    public static boolean exists(String path) throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists(path, false);
        System.out.println(exists);
        return exists != null;
    }

    public static void main(String[] args) {
        String path = "/zkFromJava";

        try {
            connection = new ZookeeperConnection();
            zooKeeper = connection.connect("47.105.97.246:2181");
            exists(path);
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
