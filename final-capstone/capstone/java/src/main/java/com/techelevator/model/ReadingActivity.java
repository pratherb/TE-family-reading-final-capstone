package com.techelevator.model;

public class ReadingActivity {

    private int id;
    private String username;
    private String bookIsbn;
    private int minutesRead;
    private String format;
    private String notes;

    public ReadingActivity() {
    }

    public ReadingActivity(int id, String username, String bookIsbn, int minutesRead, String format, String notes) {
        this.id = id;
        this.username = username;
        this.bookIsbn = bookIsbn;
        this.minutesRead = minutesRead;
        this.format = format;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityUsername() {
        return username;
    }

    public void setActivityUsername(String username) {
        this.username = username;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public int getMinutesRead() {
        return minutesRead;
    }

    public void setMinutesRead(int minutesRead) {
        this.minutesRead = minutesRead;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
