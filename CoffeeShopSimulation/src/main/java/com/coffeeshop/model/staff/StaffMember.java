package com.coffeeshop.model.staff;

import com.coffeeshop.model.order.Order;
import com.coffeeshop.util.EventLogger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class StaffMember {
    private final String name;
    private Order currentOrder;
    private int ordersProcessed;

    public StaffMember(String name) {
        this.name = name;
        this.ordersProcessed = 0;
    }

    public void processOrder(Order order) {
        this.currentOrder = order;
        order.setStatus(OrderStatus.PROCESSING);
        EventLogger.getInstance().log(name + " started processing order: " + order.getOrderId());

        try {
            // Simulate processing time (1-5 seconds)
            int processingTime = new Random().nextInt(4000) + 1000;
            TimeUnit.MILLISECONDS.sleep(processingTime);
            
            order.setStatus(OrderStatus.COMPLETED);
            ordersProcessed++;
            EventLogger.getInstance().log(name + " completed order: " + order.getOrderId() + 
                " in " + (processingTime/1000.0) + " seconds");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            order.setStatus(OrderStatus.CANCELLED);
            EventLogger.getInstance().log(name + " was interrupted while processing order: " + order.getOrderId());
        } finally {
            this.currentOrder = null;
        }
    }

    public String getName() {
        return name;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public int getOrdersProcessed() {
        return ordersProcessed;
    }

    @Override
    public String toString() {
        return name + (currentOrder != null ? 
            " (Processing Order #" + currentOrder.getOrderId().substring(0, 8) + ")" : 
            " (Available)");
    }
}