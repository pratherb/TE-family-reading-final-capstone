package com.techelevator.dao;

import com.techelevator.model.Book;

import java.util.List;

public interface BookDao {

    List<Book> searchBooksByTitle(String title);

    List<Book> searchBooksByIsbn(String isbn);

    Book addBookToReadingList(Book book, String username);

    List<Book> getFamilyReadingList(int familyId);

    List<Book> getUserReadingList(String username);

}
