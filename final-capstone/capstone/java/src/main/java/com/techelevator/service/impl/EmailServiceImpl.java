package com.techelevator.service.impl;

import com.techelevator.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String name) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("bookbound.noreply@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("New family member added to Bookbound");
        simpleMailMessage.setText("A new family member, " + name + ", was just added to your group on Bookbound. Check" +
                " out their reading list and enjoy some friendly competition for prizes!");

        this.mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendReadingListEmail(String to, String name, String isbn) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("bookbound.noreply@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Book added to reading list for" + name);
        simpleMailMessage.setText("Hello! Your child, " + name + ", just added the book with ISBN " + isbn + " to" +
                " their reading list. Happy reading!");

        this.mailSender.send(simpleMailMessage);
    }
}
