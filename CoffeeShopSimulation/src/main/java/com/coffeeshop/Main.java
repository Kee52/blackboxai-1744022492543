package com.coffeeshop;

import com.coffeeshop.controller.CoffeeShopController;
import com.coffeeshop.model.CoffeeShop;
import com.coffeeshop.view.CoffeeShopGUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                CoffeeShop model = new CoffeeShop();
                CoffeeShopGUI view = new CoffeeShopGUI();
                CoffeeShopController controller = new CoffeeShopController(model, view);
                
                // Set up proper shutdown handling
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    controller.shutdown();
                }));
                
                controller.initialize();
            } catch (Exception e) {
                System.err.println("Application failed to start:");
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}