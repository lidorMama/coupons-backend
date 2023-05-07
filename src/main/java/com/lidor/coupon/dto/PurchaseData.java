package com.lidor.coupon.dto;

import java.util.Date;

public class PurchaseData {
    private Long id;
    private int amountOfCouponToBuy;
    private Date timestamp;
    private String firstName;
    private String lastName;
    private String couponName;
    private Float price;
    private String description;

    public PurchaseData() {
    }

    public PurchaseData(Long id, int amountOfCouponToBuy, Date timestamp, String firstName, String lastName, String couponName, Float price, String description) {
        this.id = id;
        this.amountOfCouponToBuy = amountOfCouponToBuy;
        this.timestamp = timestamp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.couponName = couponName;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmountOfCouponToBuy() {
        return amountOfCouponToBuy;
    }

    public void setAmountOfCouponToBuy(int amountOfCouponToBuy) {
        this.amountOfCouponToBuy = amountOfCouponToBuy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PurchaseData{" +
                "id=" + id +
                ", amountOfCouponToBuy=" + amountOfCouponToBuy +
                ", timestamp=" + timestamp +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", couponName='" + couponName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}