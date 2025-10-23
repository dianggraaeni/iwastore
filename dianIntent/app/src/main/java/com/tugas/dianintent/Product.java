package com.tugas.dianintent;

public class Product {
    private String name;
    private String description;
    private String price;
    private int imageResource;
    private String category; // <-- DITAMBAHKAN

    public Product(String name, String description, String price, int imageResource, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResource = imageResource;
        this.category = category; // <-- DITAMBAHKAN
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public int getImageResource() { return imageResource; }
    public String getCategory() { return category; } // <-- DITAMBAHKAN
}