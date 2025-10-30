package library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import library.model.*;

public class Library {
    private final List<Lendable> items = new ArrayList<>();

    public void addItem(Lendable item) {
        items.add(item);
    }

    public void removeItem(Lendable item) {
        items.remove(item);
    }

    public List<Lendable> getAvailableItems() {
        List<Lendable> availableItems = new ArrayList<>();
        for (Lendable item : items) {
            if (!item.isBorrowed()) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }


    public void listAllItems() {
        items.forEach(item -> System.out.println(item.getDisplayInfo()));
    }
}
