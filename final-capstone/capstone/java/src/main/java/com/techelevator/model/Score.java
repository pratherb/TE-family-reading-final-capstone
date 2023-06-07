package com.techelevator.model;

public class Score {
    private int minutes;
    private String username;

    public Score(int minutes, String username) {
        this.minutes = minutes;
        this.username = username;
    }

    public Score() {
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "" + this.username + " has read " + this.minutes + " minutes";
    }
}
