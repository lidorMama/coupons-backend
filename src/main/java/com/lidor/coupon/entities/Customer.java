package com.lidor.coupon.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private User user;

    @Id
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "ADDRESS", nullable = true)
    private String address;

    @Column(name = "FIRST_NAME", nullable = true)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = true)
    private String lastName;

    @Column(name = "AMOUNT_OF_KIDS", nullable = true)
    private String amountOfKids;

    @Column(name = "PHONE_NUMBER", nullable = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE)
    private List<Purchase> purchases;

    public Customer() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
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
        return "CustomerEntity{" +
                "user=" + user +
                ", id=" + id +
                ", address='" + address + '\'' +
                ", amountOfKids='" + amountOfKids + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
