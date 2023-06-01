package com.techelevator.controller;

import com.techelevator.dao.BookDao;
import com.techelevator.dao.FamilyDao;
import com.techelevator.model.Book;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public Book addBookToReadingList(Book book, Principal principal) {
        return bookDao.addBookToReadingList(book, principal.getName());
    }

    @RequestMapping(value = ENDPOINT + "all", method = RequestMethod.GET)
    public List<Book> getFamilyReadingList(Principal principal) {
        int familyId = familyDao.getFamilyIdByUsername(principal.getName());
        return bookDao.getFamilyReadingList(familyId);
    }

    @RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.GET)
    public List<Book> getUserReadingList(String username) {
        return bookDao.getUserReadingList(username);
    }

        //Search by ISBN
        @RequestMapping(value = ENDPOINT + "/isbn={isbn}", method = RequestMethod.GET)
        public List<Book> queryForBookByIsbn (@PathVariable String isbn){
            return bookDao.searchBooksByIsbn(isbn);
    }
}
