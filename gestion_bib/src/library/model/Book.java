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
        if (isBorrowed()) {
            borrowed = true;
        } else {
            System.out.println("This book is already borrowed.");
        }
    }

    @Override
    public void returnItem() {
        if (borrowed) {
            borrowed = false;
        } else {
            System.out.println("This book was not borrowed.");
        }
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    @Override
    public boolean isBorrowed() { return !borrowed; }

    public void complexMethodExample(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println(classifyNumber(i));
        }
    }

    private String classifyNumber(int number) {
        if (number % 2 == 0) {
            return number + " is even";
        } else if (number % 3 == 0) {
            return number + " divisible by 3";
        } else {
            return number + " something else";
        }
    }

    @Override
    public String getDisplayInfo() {
        return title + " by " + author + (borrowed ? " [Borrowed]" : " [Available]");
    }
}
