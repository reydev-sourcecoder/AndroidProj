package com.haiyangrpdev.myapplication.DB;

public class Person {
    private String mName;
    private String mId;
    private int mPhoneNumber;

    public Person () { }

    public Person (String name) {
        this.mName = name;
    }

    public Person(String name, int mobileNumber) {
        this.mName = name;
        this.mPhoneNumber = mobileNumber;
    }

    public void setName(String name) { this.mName = name;}
    public void setMobileNumber (int phoneNumber) { this.mPhoneNumber = phoneNumber;}
    public void setId(String id) { this.mId = id; }

    public String getName() { return this.mName;}
    public String getId() {return this.mId; }
    public int getMobileNumber() { return this.mPhoneNumber;}
}