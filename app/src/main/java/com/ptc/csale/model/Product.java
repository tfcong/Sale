package com.ptc.csale.model;

/**
 * Created by phamthanhcong on 11/11/2017.
 */

public class Product {
    String name,phoneNumber,category,title,price,address,thumbnail,time;
    public Product() {
    }
    // title price time address thumbnail
    public Product(String title, String price, String address, String thumbnail, String time) {
        this.title = title;
        this.price = price;
        this.address = address;
        this.thumbnail = thumbnail;
        this.time = time;
    }

    public Product(String name, String phoneNumber, String category, String title, String price, String address, String thumbnail, String time) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.title = title;
        this.price = price;
        this.address = address;
        this.thumbnail = thumbnail;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
