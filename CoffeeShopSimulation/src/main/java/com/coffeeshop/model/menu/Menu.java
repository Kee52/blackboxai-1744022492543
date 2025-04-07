package com.coffeeshop.model.menu;

import com.coffeeshop.exception.InvalidMenuItemException;
import java.util.*;

public class Menu {
    private Map<String, MenuItem> items;
    private Set<String> categories;

    public Menu() {
        this.items = new HashMap<>();
        this.categories = new HashSet<>();
    }

    public void loadFromFile(String filePath) throws InvalidMenuItemException {
        // Implementation would read from file and call addItem for each line
        // Example format: BEV001,Espresso,beverages,2.50,Strong black coffee
    }

    public void addItem(MenuItem item) {
        items.put(item.getId(), item);
        categories.add(item.getCategory());
    }

    public MenuItem getItem(String id) {
        return items.get(id);
    }

    public List<MenuItem> getItemsByCategory(String category) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : items.values()) {
            if (item.getCategory().equals(category)) {
                result.add(item);
            }
        }
        return result;
    }

    public Set<String> getCategories() {
        return new HashSet<>(categories);
    }

    public List<MenuItem> getAllItems() {
        return new ArrayList<>(items.values());
    }

    public boolean containsItem(String id) {
        return items.containsKey(id);
    }
}