package models;

import java.time.LocalDate;

public class Request {

    private int requestId;
    private User user;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String requestStatus;

    public Request(int requestId, User user, Book book, LocalDate borrowDate, LocalDate returnDate, String requestStatus) {
        this.requestId = requestId;
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.requestStatus = requestStatus;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "Request{" + "requestId=" + requestId + ", user=" + user + ", book=" + book + ", borrowDate=" + borrowDate + ", returnDate=" + returnDate + ", requestStatus=" + requestStatus + '}';
    }
}
