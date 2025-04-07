package com.coffeeshop.util;

import com.coffeeshop.model.menu.Menu;
import com.coffeeshop.model.menu.MenuItem;
import com.coffeeshop.model.order.Order;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public void generateReport(Menu menu, List<Order> orders) {
        String timestamp = LocalDateTime.now().format(dtf);
        String filename = "coffee_shop_report_" + timestamp + ".txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Sales by item
            Map<String, Integer> itemCounts = new HashMap<>();
            Map<String, Double> itemRevenue = new HashMap<>();
            
            for (Order order : orders) {
                for (MenuItem item : order.getItems()) {
                    itemCounts.put(item.getId(), 
                        itemCounts.getOrDefault(item.getId(), 0) + 1);
                    itemRevenue.put(item.getId(),
                        itemRevenue.getOrDefault(item.getId(), 0.0) + item.getPrice());
                }
            }

            // Write report header
            writer.write("COFFEE SHOP SALES REPORT\n");
            writer.write("Generated at: " + LocalDateTime.now() + "\n\n");
            writer.write("Total Orders Processed: " + orders.size() + "\n\n");

            // Write item sales breakdown
            writer.write("ITEM SALES BREAKDOWN:\n");
            writer.write("----------------------------------------\n");
            writer.write(String.format("%-10s %-20s %-6s %-10s%n", 
                "ID", "Name", "Count", "Revenue"));
            
            for (MenuItem item : menu.getAllItems()) {
                String id = item.getId();
                writer.write(String.format("%-10s %-20s %-6d £%-9.2f%n",
                    id,
                    item.getName(),
                    itemCounts.getOrDefault(id, 0),
                    itemRevenue.getOrDefault(id, 0.0)));
            }

            // Calculate and write totals
            double totalRevenue = orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
            double totalDiscounts = orders.stream()
                .mapToDouble(Order::getDiscount)
                .sum();

            writer.write("\nSUMMARY:\n");
            writer.write("----------------------------------------\n");
            writer.write(String.format("Total Revenue: £%.2f%n", totalRevenue));
            writer.write(String.format("Total Discounts Given: £%.2f%n", totalDiscounts));
            writer.write(String.format("Net Revenue: £%.2f%n", totalRevenue - totalDiscounts));

            EventLogger.getInstance().log("Report generated: " + filename);
        } catch (IOException e) {
            EventLogger.getInstance().log("Failed to generate report: " + e.getMessage());
        }
    }
}