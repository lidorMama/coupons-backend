package com.lidor.coupon.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Coupons")
public class Coupon {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private float price;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "AMOUNT", nullable = false)
    private int amount;

    @Column(name = "IMG_URL", nullable = true)
    private String imgURL;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "coupon",cascade = CascadeType.REMOVE)
    private List<Purchase> purchases;


    public Coupon() {
    }

    public Coupon(Long id, String name, String description, float price, Date startDate, Date endDate, int amount,String imgURL,Category category, Company company) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imgURL = imgURL;
        this.category = category;
        this.company = company;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", category=" + category +
                ", company=" + company +
                '}';
    }
}


