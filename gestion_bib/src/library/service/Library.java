package library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import library.model.*;

public class Library {
    private List<Lendable> items;
    private ItemPrinter printer;

    public Library() {
        items = new ArrayList<>();
        printer = new ItemPrinter();
    }

    public void addItem(Lendable item) {
        items.add(item);
    }

    public void removeItem(Lendable item) {
        items.remove(item);
    }

    public List<Lendable> getAvailableItems() {
        List<Lendable> availableItems = new ArrayList<>();
        for (Lendable item : items) {
            if (!item.isBorrowed()) {  // ✅ Correction
                availableItems.add(item);
            }
        }
        return availableItems;
    }

    public void listAllItems() {
        for (Lendable item : items) {
            System.out.println(item.getDisplayInfo());
        }
    }
}
