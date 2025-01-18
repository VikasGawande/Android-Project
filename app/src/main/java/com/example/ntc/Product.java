package com.example.ntc;

import android.graphics.Bitmap;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private Bitmap image;

    public Product(int id, String name, String description, double price, Bitmap image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Bitmap getImage() {
        return image;
    }
}
