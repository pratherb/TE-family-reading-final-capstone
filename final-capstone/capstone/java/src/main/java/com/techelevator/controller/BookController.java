package com.techelevator.controller;

import com.techelevator.dao.BookDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
public class BookController {

    private BookDao bookDao;
    private final String ENDPOINT = "/book";

    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @RequestMapping(value = ENDPOINT + "/{title}", method = RequestMethod.GET)
    public String queryForBook(@PathVariable String title){
        return bookDao.searchBooksByTitle(title);
    }
}
