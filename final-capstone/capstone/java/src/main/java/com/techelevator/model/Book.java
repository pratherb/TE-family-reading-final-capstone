package com.techelevator.model;

public class Book {

    private String title;
    private String author;
    private String isbn;
    private int numPages;
    private String publisher;
    private String coverUrl;
    //TBD: private String bookCoverUrl

    public Book(String title, String author, String isbn, int numPages, String publisher, String coverUrl) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.numPages = numPages;
        this.publisher = publisher;
        this.coverUrl = coverUrl;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
