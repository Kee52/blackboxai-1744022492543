package com.coffeeshop.model.menu;

import com.coffeeshop.exception.InvalidMenuItemException;

public class MenuItem {
    private final String id;
    private final String name;
    private final String category;
    private final double price;
    private final String description;

    public MenuItem(String id, String name, String category, double price, String description) 
            throws InvalidMenuItemException {
        if (!isValidId(id)) {
            throw new InvalidMenuItemException("Invalid ID format: " + id);
        }
        if (price <= 0) {
            throw new InvalidMenuItemException("Price must be positive");
        }

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    private boolean isValidId(String id) {
        // Example format: BEV001, FOOD101
        return id != null && id.matches("^[A-Z]{3}\\d{3}$");
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("%s - %s (£%.2f)", id, name, price);
    }
}