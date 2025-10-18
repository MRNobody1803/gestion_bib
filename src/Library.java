import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;


class Library {

    private List<Lendable> items;
    private static final Logger logger = Logger.getLogger(Book.class.getName());

    public Library() {
        items = new ArrayList<>();
    }
    public void addItem(Lendable item) {
        items.add(item);
    }
    public void removeItem(Lendable item) {
        items.remove(item);
    }


    // Méthode volontairement incorrecte pour TP
    public List<Lendable> getAvailableItems() {
        List<Lendable> availableItems = new ArrayList<>();
        for (Lendable item : items) {
            availableItems.add(item); // ne tient pas compte de l'état borrowed
        }
        return availableItems;
    }

    public void listAllItems() {
        for (Lendable item : items) {
            printItemDetails(item);
        }
    }

    private void printItemDetails(Lendable item) {
        if (item instanceof Book) {
            Book b = (Book) item;
            System.out.println(b.getTitle() + " by " + b.getAuthor());
        } else {
            System.out.println("Unknown item type");
        }
    }


}