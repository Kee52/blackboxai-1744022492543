package com.coffeeshop.model.order;

import com.coffeeshop.model.menu.MenuItem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final String customerId;
    private final LocalDateTime timestamp;
    private final List<MenuItem> items;
    private double total;
    private double discount;
    private OrderStatus status;

    public Order(String customerId, List<MenuItem> items) {
        this.orderId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.timestamp = LocalDateTime.now();
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PENDING;
        calculateTotal();
    }

    private void calculateTotal() {
        this.total = items.stream().mapToDouble(MenuItem::getPrice).sum();
        applyDiscounts();
    }

    private void applyDiscounts() {
        // Example discount logic: 20% off if beverage + 2 food items
        long beverageCount = items.stream()
            .filter(item -> item.getCategory().equals("beverages"))
            .count();
        long foodCount = items.stream()
            .filter(item -> item.getCategory().equals("food"))
            .count();

        if (beverageCount >= 1 && foodCount >= 2) {
            this.discount = total * 0.2;
            this.total -= discount;
        }
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public List<MenuItem> getItems() { return new ArrayList<>(items); }
    public double getTotal() { return total; }
    public double getDiscount() { return discount; }
    public OrderStatus getStatus() { return status; }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Order #%s - Customer: %s - Total: £%.2f (%s)",
                orderId.substring(0, 8), customerId, total, status);
    }
}