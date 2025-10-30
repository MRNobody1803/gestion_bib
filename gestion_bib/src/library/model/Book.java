package library.model;
//Comet 1


public class Book implements Lendable {
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
        if (borrowed) {
            warn("This book is already borrowed.");
            return;
        }
        borrowed = true;
    }

    @Override
    public void returnItem() {
        if (!borrowed) {
            warn("This book was not borrowed.");
            return;
        }
        borrowed = false;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public boolean isBorrowed() { return borrowed; }

    public void complexMethodExample(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println(classifyNumber(i));
        }
    }

    private String classifyNumber(int number) {
        if (number % 2 == 0) return number + " is even";
        if (number % 3 == 0) return number + " divisible by 3";
        return number + " something else";
    }

    @Override
    public String getDisplayInfo() {
        return String.format("%s by %s [%s]", title, author, borrowed ? "Borrowed" : "Available");
    }


    private void warn(String message) {
        System.out.println("[Warning] " + message);
    }
}