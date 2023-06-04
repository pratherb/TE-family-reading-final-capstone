package com.techelevator.controller;

import com.techelevator.dao.BookDao;
import com.techelevator.dao.FamilyDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Book;
import com.techelevator.model.Family;
import com.techelevator.model.User;
import com.techelevator.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;
    private FamilyDao familyDao;
    private BookDao bookDao;
    private final EmailService emailService;
    private final String ENDPOINT = "/user";

    public UserController(UserDao userDao, BookDao bookDao, FamilyDao familyDao, EmailService emailService) {
        this.userDao = userDao;
        this.familyDao = familyDao;
        this.bookDao = bookDao;
        this.emailService = emailService;
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public User createFamilyMember(@RequestBody User user, Principal principal) {
        int familyId = familyDao.getFamilyIdByUsername(principal.getName());
        int newUserId = userDao.create(
                user.getUsername(), user.getPassword(), "child",
                user.getFirstName(), user.getLastName(), familyId
        );
        Family family = familyDao.getFamilyById(familyId);
        String email = family.getEmail();
        this.emailService.sendEmail(email, user.getFirstName());
        return userDao.getUserById(newUserId);
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
    public List<User> getUsersByFamilyId(Principal principal){
        int id = familyDao.getFamilyIdByUsername(principal.getName());
        return userDao.getUsersByFamilyId(id);
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.DELETE)
    public void deleteUserByUsername(@RequestParam String username, Principal principal){
        if (principal.getName().equals(username)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete current user");
        } else {
            userDao.delete(username);
        }
    }

    @RequestMapping(value = ENDPOINT + "/reading", method = RequestMethod.GET)
    public List<Book> getUserReadingList(@RequestParam String username) {
        return bookDao.getUserReadingList(username);
    }

    @RequestMapping(value = ENDPOINT + "/reading/isbn={isbn}", method = RequestMethod.POST)
    public Book addBookToReadingList(@PathVariable String isbn, @RequestParam String username) {
        return bookDao.addBookToReadingList(bookDao.searchBookByIsbn(isbn), username);
    }

}
