package com.techelevator.dao;

import com.techelevator.model.Book;

import java.util.List;

public interface BookDao {

    String searchBooksByTitle(String title);

    Book addBookToReadingList(Book book, String username);

    List<Book> getFamilyReadingList(int familyId);

    List<Book> getUserReadingList(String username);

}
