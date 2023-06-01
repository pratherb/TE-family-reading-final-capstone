package com.techelevator.dao;

import com.techelevator.model.Book;

import java.util.List;

public interface BookDao {

    List<Book> searchBooksByTitle(String title);
    List<Book> searchBooksByIsbn(String isbn);

}
