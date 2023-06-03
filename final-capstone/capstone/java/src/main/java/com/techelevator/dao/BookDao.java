package com.techelevator.dao;

import com.techelevator.model.Book;

import java.util.List;

public interface BookDao {

    //Calls to Open Library
    List<Book> searchBooksByTitle(String title);
    Book searchBookByIsbn(String isbn);

    //Handle books
    Book createBook(Book book);

    //Handle reading list
    Book addBookToReadingList(Book book, String username);
    List<Book> getFamilyReadingList(int familyId, boolean finished);
    List<Book> getUserReadingList(String username, boolean finished);

    void deleteBookById (String isbn);

}
