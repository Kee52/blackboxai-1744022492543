package com.coffeeshop.model.order;

public enum OrderStatus {
    PENDING,    // Order placed but not yet processed
    PROCESSING, // Currently being prepared
    COMPLETED,  // Order fulfilled
    CANCELLED   // Order cancelled
}