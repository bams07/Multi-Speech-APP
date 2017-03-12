package com.bams.android.multispeechapp.Domain;

import com.bams.android.multispeechapp.Constants.ProductStatus;

import java.util.Date;

/**
 * Created by bams on 3/11/17.
 */

public class Product {

    public String name;
    public String brand;
    public String quantity;
    public Date createdAt;
    public String status;

    public Product() {
    }

    public Product(String name, String brand, String quantity, Date createdAt, String status) {
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.status = status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getQuantity() {
        return quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }
}
