package com.arpan.itinterview.tables;

public class ContactUs {

    String mobileNo;
    String email;

    public ContactUs(){

    }

    public ContactUs(String mobileNo, String email, String address) {
        this.mobileNo = mobileNo;
        this.email = email;
        this.address = address;
    }

    public String getMobileNo() {

        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    String address;
}
