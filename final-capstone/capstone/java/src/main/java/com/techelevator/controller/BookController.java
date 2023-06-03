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

//    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
//    public Book addBookToReadingList(Book book, Principal principal) {
//        return bookDao.addBookToReadingList(book, principal.getName());
//    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public Book addBookToReadingList(Book book, String username) {
        return bookDao.addBookToReadingList(book, username);
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.DELETE)
    public void deleteBook(String isbn) {
        bookDao.deleteBookById(isbn);
    }

    @RequestMapping(value = ENDPOINT + "all", method = RequestMethod.GET)
    public List<Book> getFamilyReadingList(Principal principal, boolean finished) {
        int familyId = familyDao.getFamilyIdByUsername(principal.getName());
        return bookDao.getFamilyReadingList(familyId, finished);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public List<Book> getUserReadingList(@PathVariable String username, boolean finished) {
        return bookDao.getUserReadingList(username, finished);
    }

    //Search by ISBN
    @RequestMapping(value = ENDPOINT + "/isbn={isbn}", method = RequestMethod.GET)
    public Book queryForBookByIsbn(@PathVariable String isbn) {
        return bookDao.searchBookByIsbn(isbn);
    }

    //TBD

    //Create book by ISBN
    @RequestMapping(value = ENDPOINT + "/isbn={isbn}", method = RequestMethod.POST)
    public Book create(@PathVariable String isbn) {
        //Try to call Open Library and get book data
        Book newBook = bookDao.searchBookByIsbn(isbn);
        return bookDao.createBook(newBook);
    }

}
