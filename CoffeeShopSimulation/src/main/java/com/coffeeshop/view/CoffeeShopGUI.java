package com.coffeeshop.view;

import com.coffeeshop.controller.CoffeeShopController;
import com.coffeeshop.model.menu.MenuItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CoffeeShopGUI extends JFrame {
    private final CoffeeShopController controller;
    private JList<MenuItem> menuList;
    private DefaultListModel<MenuItem> menuListModel;
    private JTextArea orderDetailsArea;
    private JLabel statusLabel;
    private JList<String> queueList;
    private DefaultListModel<String> queueListModel;
    private JList<String> staffList;
    private DefaultListModel<String> staffListModel;

    public CoffeeShopGUI(CoffeeShopController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Coffee Shop Simulation");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main content panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 3));

        // Menu panel
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        menuListModel = new DefaultListModel<>();
        menuList = new JList<>(menuListModel);
        menuList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        menuPanel.add(new JScrollPane(menuList), BorderLayout.CENTER);

        // Order details panel
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));
        orderDetailsArea = new JTextArea();
        orderDetailsArea.setEditable(false);
        orderPanel.add(new JScrollPane(orderDetailsArea), BorderLayout.CENTER);

        // Simulation panel
        JPanel simulationPanel = new JPanel(new GridLayout(2, 1));
        
        // Queue panel
        JPanel queuePanel = new JPanel(new BorderLayout());
        queuePanel.setBorder(BorderFactory.createTitledBorder("Order Queue"));
        queueListModel = new DefaultListModel<>();
        queueList = new JList<>(queueListModel);
        queuePanel.add(new JScrollPane(queueList), BorderLayout.CENTER);

        // Staff panel
        JPanel staffPanel = new JPanel(new BorderLayout());
        staffPanel.setBorder(BorderFactory.createTitledBorder("Staff Status"));
        staffListModel = new DefaultListModel<>();
        staffList = new JList<>(staffListModel);
        staffPanel.add(new JScrollPane(staffList), BorderLayout.CENTER);

        simulationPanel.add(queuePanel);
        simulationPanel.add(staffPanel);

        mainPanel.add(menuPanel);
        mainPanel.add(orderPanel);
        mainPanel.add(simulationPanel);

        // Control buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add to Order");
        addButton.addActionListener(e -> addSelectedItemsToOrder());
        buttonPanel.add(addButton);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(e -> placeOrder());
        buttonPanel.add(placeOrderButton);

        JButton startSimButton = new JButton("Start Simulation");
        startSimButton.addActionListener(e -> controller.startSimulation());
        buttonPanel.add(startSimButton);

        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> controller.generateReport());
        buttonPanel.add(generateReportButton);

        // Status bar
        statusLabel = new JLabel("Ready");
        statusLabel.setBorder(BorderFactory.createEtchedBorder());

        // Add components to frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(statusLabel, BorderLayout.NORTH);
    }

    public void updateMenuList(List<MenuItem> items) {
        menuListModel.clear();
        for (MenuItem item : items) {
            menuListModel.addElement(item);
        }
    }

    public void updateOrderDetails(String details) {
        orderDetailsArea.setText(details);
    }

    public void updateQueueList(List<String> queueItems) {
        queueListModel.clear();
        for (String item : queueItems) {
            queueListModel.addElement(item);
        }
    }

    public void updateStaffList(List<String> staffStatuses) {
        staffListModel.clear();
        for (String status : staffStatuses) {
            staffListModel.addElement(status);
        }
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    private void addSelectedItemsToOrder() {
        List<MenuItem> selectedItems = menuList.getSelectedValuesList();
        controller.addItemsToCurrentOrder(selectedItems);
    }

    private void placeOrder() {
        controller.placeCurrentOrder();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}