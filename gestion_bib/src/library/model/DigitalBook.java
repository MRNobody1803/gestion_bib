package library.model;

public class DigitalBook extends Book {
    private int fileSizeMB;

    public DigitalBook(String title, String author, int fileSizeMB) {
        super(title, author);
        this.fileSizeMB = fileSizeMB;
    }

    @Override
    public String getDisplayInfo() {
        String status = isBorrowed() ? " [Emprunté]" : " [Disponible]";
        return getTitle() + " par " + getAuthor() +
                " (" + fileSizeMB + " MB)" + status;
    }

    public int getFileSizeMB() {
        return fileSizeMB;
    }
}
