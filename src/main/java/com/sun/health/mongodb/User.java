package com.sun.health.mongodb;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 华硕 on 2018-07-02.
 */
public class User implements Serializable {

    private static final long serialVersionUID = -1706691619389519278L;

    @Id
    private String _id;

    private String name;

    private Integer age;

    private String gender;

    private List<Address> address;

    public User() {
    }

    public User(String name, Integer age, String gender, List<Address> address) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Address> getAddresses() {
        return address;
    }

    public void setAddresses(List<Address> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", address=" + address +
                '}';
    }
}
