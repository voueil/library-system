package models;

public class Book {

    private String ISBN;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String ISBN, String title, String author, boolean isAvailable) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Book{" + "ISBN=" + ISBN + ", title=" + title + ", author=" + author + ", isAvailable=" + isAvailable + '}';
    }

}
