package com.techelevator.controller;

import com.techelevator.dao.BookDao;
import com.techelevator.model.Book;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
public class BookController {

    private BookDao bookDao;
    private final String ENDPOINT = "/book";

    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @RequestMapping(value = ENDPOINT + "/title={title}", method = RequestMethod.GET)
    public List<Book> queryForBookByTitle(@PathVariable String title){
        return bookDao.searchBooksByTitle(title);
    }

    //Search by ISBN
    @RequestMapping(value = ENDPOINT + "/isbn={isbn}", method = RequestMethod.GET)
    public List<Book> queryForBookByIsbn(@PathVariable  String isbn){
        return  bookDao.searchBooksByIsbn(isbn);
    }

    //TBD

    //Create book by ISBN
    @RequestMapping(value = ENDPOINT + "/isbn={isbn}", method = RequestMethod.POST)
    public Book create(@PathVariable String isbn){
        return null;
    }

}
