package com.aggarwalcode.onlinestore.ecomexampleb;

import com.google.firebase.database.Exclude;

/**
 * Created by Lenovo-64 on 2/11/2018.
 */

public class ProductsHolder {
    @Exclude
    private String imageUrl;
    private String brand;
    private String description;
    private int price;
    private String other;
    private String name;
    public String key;

    private ProductsHolder(){}
    public ProductsHolder(String name, String imageUrl, String brand, String description, String other, int price, String key) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.other = other;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
