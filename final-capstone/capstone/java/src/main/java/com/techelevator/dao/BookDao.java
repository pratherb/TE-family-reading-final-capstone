package com.techelevator.dao;

import com.techelevator.model.Book;

import java.security.Principal;
import java.util.List;

public interface BookDao {

    //Calls to Open Library
    List<Book> searchBooksByTitle(String title);
    Book searchBookByIsbn(String isbn);

    Book addBookToReadingListByPrincipal(Book book, String username);

    //Handle books
    Book createBook(Book book);
    void delete(String isbn);

    //Handle reading list
    Book addBookToReadingList(Book book, String username);
    int getFamilyBooksFinished(int familyId);
    List<Book> getUserCurrentlyReading(String username);
    List<Book> getFamilyReadingListByCompletion(int familyId, Boolean completed);
    Book updateReadingListEntryByIsbn(Book book, String isbn);
    void deleteReadingListEntryByIsbn(String isbn);
    int getNumberOfBooksFinished(String username);
    void markBookAsFinished(String username, String isbn);
}
