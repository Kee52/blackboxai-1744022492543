package com.coffeeshop.model;

import com.coffeeshop.exception.InvalidMenuItemException;
import com.coffeeshop.model.menu.Menu;
import com.coffeeshop.model.menu.MenuItem;
import com.coffeeshop.model.order.Order;
import com.coffeeshop.model.order.OrderQueue;
import com.coffeeshop.model.staff.StaffManager;
import com.coffeeshop.util.EventLogger;
import com.coffeeshop.util.ReportGenerator;

import java.util.List;
import java.util.Map;

public class CoffeeShop {
    private Menu menu;
    private OrderQueue orderQueue;
    private StaffManager staffManager;
    private ReportGenerator reportGenerator;
    private EventLogger eventLogger;

    public CoffeeShop() {
        this.menu = new Menu();
        this.orderQueue = new OrderQueue();
        this.staffManager = new StaffManager(3); // 3 staff members
        this.reportGenerator = new ReportGenerator();
        this.eventLogger = EventLogger.getInstance();
    }

    public void loadMenu(String filePath) throws InvalidMenuItemException {
        menu.loadFromFile(filePath);
        eventLogger.log("Menu loaded from: " + filePath);
    }

    public void loadOrders(String filePath) {
        orderQueue.loadInitialOrders(filePath, menu);
        eventLogger.log("Initial orders loaded from: " + filePath);
    }

    public void placeNewOrder(Order order) {
        orderQueue.addOrder(order);
        eventLogger.log("New order placed by customer: " + order.getCustomerId());
    }

    public void startSimulation() {
        staffManager.startProcessingOrders(orderQueue);
        eventLogger.log("Simulation started with " + staffManager.getStaffCount() + " staff members");
    }

    public void generateReport() {
        reportGenerator.generateReport(menu, orderQueue.getCompletedOrders());
        eventLogger.log("Report generated");
    }

    public Menu getMenu() {
        return menu;
    }

    public OrderQueue getOrderQueue() {
        return orderQueue;
    }

    public StaffManager getStaffManager() {
        return staffManager;
    }
}