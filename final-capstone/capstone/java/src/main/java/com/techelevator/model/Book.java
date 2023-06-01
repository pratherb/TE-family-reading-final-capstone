package com.techelevator.model;

public class Book {

    private String title;
    private String author;
    private String isbn;
    private int numPages;
    //TBD: private String bookCoverUrl

    public Book(String title, String author, String isbn, int numPages) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.numPages = numPages;
    }

    public Book() {

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
}
