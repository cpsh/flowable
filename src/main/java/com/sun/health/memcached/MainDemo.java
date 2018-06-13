package com.sun.health.memcached;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.ops.OperationStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Map;

/**
 * Created by 华硕 on 2018-06-13.
 */
public class MainDemo {

    public static class InnerUser implements Serializable {

        private static final long serialVersionUID = 5520404046885642553L;

        private String name;

        private Integer age;

        private Character gender;

        public InnerUser() {
        }

        public InnerUser(String name, Integer age, Character gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Character getGender() {
            return gender;
        }

        public void setGender(Character gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "InnerUser{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    '}';
        }
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/sun/health/memcached/application-memcached.xml");
        MemcachedClient memcachedClient = (MemcachedClient) applicationContext.getBean("spyMemcachedClient");
        Map<SocketAddress, Map<String, String>> stats = memcachedClient.getStats();
        System.out.println("=====================================================================================");
        for (Map.Entry<SocketAddress, Map<String, String>> socketAddressMapEntry : stats.entrySet()) {
            System.out.println(socketAddressMapEntry.getKey());
            System.out.println(socketAddressMapEntry.getValue());
        }
        OperationFuture<Boolean> future = memcachedClient.set("name", 30, "sunjian");
        OperationStatus status = future.getStatus();
        System.out.println(status.toString() + status.isSuccess() + status.getMessage());
        System.out.println(memcachedClient.get("name"));

        OperationFuture<Boolean> appendFuture = memcachedClient.append("name", "NB");
        OperationStatus appendStatus = appendFuture.getStatus();
        System.out.println(appendStatus.toString() + appendStatus.isSuccess() + appendStatus.getMessage());
        System.out.println(memcachedClient.get("name"));


        InnerUser innerUser = new InnerUser("孙健", 25, 'M');
        OperationFuture<Boolean> userFuture = memcachedClient.set("user", 0, innerUser);
        OperationStatus userStatus = userFuture.getStatus();
        System.out.println(userStatus.toString() + userStatus.isSuccess() + userStatus.getMessage());
        System.out.println(memcachedClient.get("user"));


        CASValue<Object> nameCASValue = memcachedClient.gets("name");
        CASResponse nameResponse = memcachedClient.cas("name", nameCASValue.getCas(), "NBsunjian");
        System.out.println(nameResponse);


        System.out.println("=====================================================================================");
        memcachedClient.shutdown();
    }

}
