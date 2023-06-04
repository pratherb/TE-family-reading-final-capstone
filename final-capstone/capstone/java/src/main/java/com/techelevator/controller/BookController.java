package com.techelevator.controller;

import com.techelevator.dao.BookDao;
import com.techelevator.dao.FamilyDao;
import com.techelevator.model.Book;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
public class BookController {

    private FamilyDao familyDao;
    private BookDao bookDao;
    private final String ENDPOINT = "/book";

    public BookController(BookDao bookDao, FamilyDao familyDao) {
        this.bookDao = bookDao;
        this.familyDao = familyDao;
    }

    @RequestMapping(value = ENDPOINT + "/title={title}", method = RequestMethod.GET)
    public List<Book> queryForBookByTitle(@PathVariable String title) {
        return bookDao.searchBooksByTitle(title);
    }

    //Search by ISBN
    @RequestMapping(value = ENDPOINT + "/isbn={isbn}", method = RequestMethod.GET)
    public Book queryForBookByIsbn(@PathVariable String isbn) {
        return bookDao.searchBookByIsbn(isbn);
    }

    //This is probably dangerous? Will delete other users books?
    @RequestMapping(value = ENDPOINT + "/isbn={isbn}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable String isbn) {
        bookDao.delete(isbn);
    }

}
