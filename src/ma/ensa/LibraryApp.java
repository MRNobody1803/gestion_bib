package ma.ensa;

import java.util.logging.Logger;

public class LibraryApp {

    private static final Logger logger = Logger.getLogger(LibraryApp.class.getName());

    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book("The Catcher in the Rye", "J.D. Salinger");
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee");
        DigitalBook book3 = new DigitalBook("Digital Fortress", "Dan Brown", 5);

        library.addItem(book1);
        library.addItem(book2);
        library.addItem(book3);

        logger.info("List of Books in the ma.ensa.Library:");
        library.listAllItems();  // utilise la méthode refactorisée

        // Méthode complexe utilisée pour l’analyse SonarQube
        book1.complexMethodExample(10);
    }
}
