package com.techelevator.dao;

import com.techelevator.model.Book;

import java.security.Principal;
import java.util.List;

public interface BookDao {

    //Calls to Open Library
    List<Book> searchBooksByTitle(String title);
    Book searchBookByIsbn(String isbn);

    Book addBookToReadingListByPrincipal(Book book, Principal principal);

    //Handle books
    Book createBook(Book book);
    void delete(String isbn);

    //Handle reading list
    Book addBookToReadingList(Book book, String username);
    List<Book> getFamilyReadingList(int familyId);
    List<Book> getUserReadingList(String username, boolean finished);
    List<Book> getFamilyReadingListByCompletion(int familyId, Boolean completed);
    Book updateReadingListEntryByIsbn(Book book, String isbn);
    void deleteReadingListEntryByIsbn(String isbn);
    int getNumberOfBooksFinished(String username);
    void markBookAsFinished(String isbn);
}
