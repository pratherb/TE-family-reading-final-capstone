package com.techelevator.model;

public class ReadingActivity {

    private int id;
    private int userId;
    private String bookIsbn;
    private int minutesRead;
    private String format;
    private String notes;

    public ReadingActivity() {
    }

    public ReadingActivity(int id, int userId, String bookIsbn, int minutesRead, String format, String notes) {
        this.id = id;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
