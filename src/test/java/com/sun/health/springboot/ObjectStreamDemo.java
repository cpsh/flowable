package com.sun.health.springboot;

import org.junit.Test;

import java.io.*;
import java.util.Date;

/**
 * Created by 华硕 on 2018-05-15.
 */
public class ObjectStreamDemo implements Serializable {

    private static final long serialVersionUID = 5339702434773265244L;

    public static class InnerUser implements Serializable {
        private static final long serialVersionUID = 3169713970076317634L;
        private int id;
        private String name;
        private int age;
        private Date birthday;
        private boolean gender;

        public InnerUser() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public boolean isGender() {
            return gender;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "InnerUser{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", birthday=" + birthday +
                    ", gender=" + gender +
                    '}';
        }
    }

    @Test
    public void test1() {
        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);
    }

    @Test
    public void test2() {
        String userDir = System.getProperty("user.dir");
        InnerUser innerUser = new InnerUser();
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(userDir + "\\\\innerUser.obj");
            if(!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(innerUser);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test3() {
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir + "/innerUser.obj");
        if(!file.exists()) {
            System.out.println("文件不存在");
            return ;
        }
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            InnerUser innerUser = (InnerUser) objectInputStream.readObject();
            System.out.println(innerUser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
