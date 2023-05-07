package com.lidor.coupon.dto;

import javax.persistence.Column;

public class CustomerData {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String address;
    private String amountOfKids;
    private String phoneNumber;


    public CustomerData(Long id, String userName, String firstName, String lastName, String address, String amountOfKids, String phoneNumber) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(String amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "CustomerData{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", amountOfKids='" + amountOfKids + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
