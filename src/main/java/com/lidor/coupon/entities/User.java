package com.lidor.coupon.entities;

import com.lidor.coupon.enums.UserType;

import javax.persistence.*;


@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_TYPE", nullable = false)
    private UserType userType;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", nullable = true)
    private Company company;


    public User() {
    }

    public User(Long id, String userName, String password, UserType userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public User(Long id, String userName, String password, UserType userType, Company company) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.company = company;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", company=" + company +
                '}';
    }
}