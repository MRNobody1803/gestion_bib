package library.model;

public class ItemPrinter {
    public void printItem(Lendable item) {
        if (item instanceof Book) {
            Book b = (Book) item;
            System.out.println(b.getTitle() + " by " + b.getAuthor());
        } else {
            System.out.println("Unknown item type");
        }
    }
}
