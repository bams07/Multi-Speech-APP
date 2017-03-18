package com.bams.android.multispeechapp.Domain;

import com.bams.android.multispeechapp.Constants.ProductStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bams on 3/11/17.
 */

public class Product {

//    public String uid;
    public String name;
    public String brand;
    public String quantity;
    public long dateTime;
    public String status;

    public Product() {
    }

    public Product(String name, String brand, String quantity, long dateTime, String status) {
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.dateTime = dateTime;
        this.status = status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public void setDateTime (long dateTime) {
        this.dateTime = dateTime;
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

    public long getDateTime(){
        return this.dateTime;
    }

    public String getStatus() {
        return status;
    }

}
