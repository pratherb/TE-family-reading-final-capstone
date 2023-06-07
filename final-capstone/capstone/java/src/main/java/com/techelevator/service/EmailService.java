package com.techelevator.service;

public interface EmailService {
    void sendEmail(String to, String name);

    void sendReadingListEmail(String to, String name, String isbn);
}
