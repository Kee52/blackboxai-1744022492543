package com.coffeeshop.model.order;

import com.coffeeshop.model.menu.Menu;
import com.coffeeshop.model.menu.MenuItem;
import java.util.*;
import java.util.concurrent.*;

public class OrderQueue {
    private final Queue<Order> pendingOrders = new ConcurrentLinkedQueue<>();
    private final List<Order> completedOrders = new CopyOnWriteArrayList<>();
    private final List<Order> allOrders = new CopyOnWriteArrayList<>();

    public void addOrder(Order order) {
        pendingOrders.add(order);
        allOrders.add(order);
    }

    public Order getNextOrder() {
        return pendingOrders.poll();
    }

    public void completeOrder(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
        completedOrders.add(order);
    }

    public void loadInitialOrders(String filePath, Menu menu) {
        // Implementation would read from file and create Order objects
        // For now, we'll add some sample orders
        addSampleOrders(menu);
    }

    private void addSampleOrders(Menu menu) {
        // Sample implementation - would be replaced with file reading
        List<MenuItem> sampleItems = new ArrayList<>();
        if (menu.getAllItems().size() > 0) {
            sampleItems.add(menu.getAllItems().get(0));
        }
        if (menu.getAllItems().size() > 1) {
            sampleItems.add(menu.getAllItems().get(1));
        }
        
        if (!sampleItems.isEmpty()) {
            addOrder(new Order("CUST001", sampleItems));
        }
    }

    public int getPendingCount() {
        return pendingOrders.size();
    }

    public int getCompletedCount() {
        return completedOrders.size();
    }

    public List<Order> getCompletedOrders() {
        return new ArrayList<>(completedOrders);
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(allOrders);
    }
}