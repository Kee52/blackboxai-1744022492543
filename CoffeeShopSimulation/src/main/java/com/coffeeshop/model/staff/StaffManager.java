package com.coffeeshop.model.staff;

import com.coffeeshop.model.order.Order;
import com.coffeeshop.model.order.OrderQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StaffManager {
    private final int staffCount;
    private final List<StaffMember> staffMembers;
    private ExecutorService executorService;

    public StaffManager(int staffCount) {
        this.staffCount = staffCount;
        this.staffMembers = new ArrayList<>();
        for (int i = 0; i < staffCount; i++) {
            staffMembers.add(new StaffMember("Staff-" + (i+1)));
        }
    }

    public void startProcessingOrders(OrderQueue orderQueue) {
        executorService = Executors.newFixedThreadPool(staffCount);
        
        for (StaffMember staff : staffMembers) {
            executorService.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    Order order = orderQueue.getNextOrder();
                    if (order != null) {
                        staff.processOrder(order);
                        orderQueue.completeOrder(order);
                    } else {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            });
        }
    }

    public void stopProcessing() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    public int getStaffCount() {
        return staffCount;
    }

    public List<StaffMember> getStaffMembers() {
        return new ArrayList<>(staffMembers);
    }
}