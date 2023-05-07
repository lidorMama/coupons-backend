package com.lidor.coupon.dto;

import com.lidor.coupon.enums.UserType;

public class SuccessfulLoginData {
    private Long id;
    private UserType userType;
    private Long companyId;

    public SuccessfulLoginData() {
    }

    public SuccessfulLoginData(Long id, UserType userType, Long companyId) {
        this.id = id;
        this.userType = userType;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "SuccessfulLoginData{" +
                "id=" + id +
                ", userType=" + userType +
                ", companyId=" + companyId +
                '}';
    }
}
