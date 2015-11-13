package com.igaitapp.virtualmd.igait;

import java.io.Serializable;

/**
 * Created by jesus on 9/27/15.
 */
public class ContactInfo implements Serializable {
    private String email, address, city, state, mobile;
    private double zipCode;

    public ContactInfo(String email, String address, String city, String state, double zipCode, String mobile) {
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
