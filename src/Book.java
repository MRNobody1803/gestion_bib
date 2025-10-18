import java.util.logging.Logger;

public class Book implements Lendable {

    private static final Logger logger = Logger.getLogger(Book.class.getName());

    private String title;
    private String author;
    private boolean borrowed;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.borrowed = false;
    }

    @Override
    public void borrow() {
        if (!borrowed) {
            borrowed = true;
            logger.info("Book borrowed: " + title);
        } else {
            logger.warning("Attempted to borrow already borrowed book: " + title);
        }
    }

    @Override
    public void returnItem() {
        if (borrowed) {
            borrowed = false;
            logger.info("Book returned: " + title);
        } else {
            logger.warning("Attempted to return a book that was not borrowed: " + title);
        }
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isBorrowed() { return borrowed; }

    public void complexMethodExample(int n) {
        for (int i = 0; i < n; i++) {
            logNumberType(i);
        }
    }

    private void logNumberType(int i) {
        if (i % 2 == 0) {
            logger.info(i + " is even");
        } else if (i % 3 == 0) {
            logger.info(i + " divisible by 3");
        } else {
            logger.info(i + " something else");
        }
    }


    // Méthode pour vérifier si le livre est disponible
    public boolean getAvailableItems() {
        return !borrowed;
    }
}
