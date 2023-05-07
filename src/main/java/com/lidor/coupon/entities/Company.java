package com.lidor.coupon.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Company")
public class Company {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "ADDRESS", unique = true, nullable = false)
    private String address;

    @OneToMany(mappedBy = "company",cascade = CascadeType.REMOVE)
    private List<Coupon>coupons;

    @OneToMany(mappedBy = "company",cascade = CascadeType.REMOVE)
    private List<User>users;

    public Company() {
    }

    public Company(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
