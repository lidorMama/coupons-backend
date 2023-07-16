package com.lidor.coupon.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "id" , nullable = false)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private User user;

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

    public Customer(User user, String address, String firstName, String lastName, String amountOfKids, String phoneNumber) {
        this.user = user;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amountOfKids = amountOfKids;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
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
