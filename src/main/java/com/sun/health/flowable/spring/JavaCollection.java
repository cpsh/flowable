package com.sun.health.flowable.spring;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JavaCollection {

    private List addressList;

    private Set addressSet;

    private Map addressMap;

    private Properties addressProps;

    public JavaCollection() {
    }

    public List getAddressList() {
        return addressList;
    }

    public void setAddressList(List addressList) {
        this.addressList = addressList;
    }

    public Set getAddressSet() {
        return addressSet;
    }

    public void setAddressSet(Set addressSet) {
        this.addressSet = addressSet;
    }

    public Map getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map addressMap) {
        this.addressMap = addressMap;
    }

    public Properties getAddressProps() {
        return addressProps;
    }

    public void setAddressProps(Properties addressProps) {
        this.addressProps = addressProps;
    }
}
