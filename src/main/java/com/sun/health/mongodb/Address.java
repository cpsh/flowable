package com.sun.health.mongodb;

import java.io.Serializable;

/**
 * Created by 华硕 on 2018-07-02.
 */
public class Address implements Serializable {

    private static final long serialVersionUID = -1L;

    private String building;

    private Integer pincode;

    private String city;

    private String state;

    public Address() {
    }

    public Address(String building, Integer pincode, String city, String state) {
        this.building = building;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Address{" +
                "building='" + building + '\'' +
                ", pincode=" + pincode +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
