package com.lidor.coupon.dto;

import com.lidor.coupon.enums.UserType;

public class UserData {
    private Long id;
    private String userName;
    private UserType userType;
    private String companyName;

    public UserData(Long id, String userName, UserType userType, String companyName) {
        this.id = id;
        this.userName = userName;
        this.userType = userType;
        this.companyName = companyName;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "UserCompany{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userType=" + userType +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}