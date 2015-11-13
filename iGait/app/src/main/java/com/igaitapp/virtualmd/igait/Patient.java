package com.igaitapp.virtualmd.igait;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jesus on 8/21/15.
 */
public class Patient implements Serializable {
    private long idNumber;
    private String lastName, firstName;
    private Date birthday, expectedWalkTime;;
    private char sex;
    private ContactInfo contactInfo;
    private List<GaitHealth> gaitHealth = new ArrayList<GaitHealth>();
    private boolean priority;

    public Patient(long idNumber, String lastName, String firstName, Date birthday, char sex, ContactInfo contactInfo, Date expectedWalkTime, List<GaitHealth> gaitHealth, boolean priority) {
        super();

        this.idNumber = idNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.sex = sex;
        this.contactInfo = contactInfo;
        this.expectedWalkTime = expectedWalkTime;
        this.gaitHealth = gaitHealth;
        this.priority = priority;
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getExpectedWalkTime() {
        return expectedWalkTime;
    }

    public void setExpectedWalkTime(Date expectedWalkTime) {
        this.expectedWalkTime = expectedWalkTime;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<GaitHealth> getGaitHealth() {
        return gaitHealth;
    }

    public void setGaitHealth(List<GaitHealth> gaitHealth) {
        this.gaitHealth = gaitHealth;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
