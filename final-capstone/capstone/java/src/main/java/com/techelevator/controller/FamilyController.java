package com.techelevator.controller;

import com.techelevator.dao.BookDao;
import com.techelevator.dao.FamilyDao;
import com.techelevator.model.Book;
import com.techelevator.model.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
//PreAuthorize("isAuthenticated()")
public class FamilyController {

    private FamilyDao familyDao;
    private BookDao bookDao;
    private final String ENDPOINT = "/family";

    public FamilyController(FamilyDao familyDao, BookDao bookDao) {
        this.familyDao = familyDao;
        this.bookDao = bookDao;
    }

    @RequestMapping(value = ENDPOINT + "/reading", method = RequestMethod.GET)
    public List<Book> getFamilyReadingList(Principal principal) {
        int familyId = familyDao.getFamilyIdByUsername(principal.getName());
        return bookDao.getFamilyReadingList(familyId);
    }

    @RequestMapping(value = ENDPOINT + "/reading/completed={completed}", method = RequestMethod.GET)
    public List<Book> getFamilyReadingListByCompletion(@PathVariable Boolean completed, Principal principal){
        int familyId = familyDao.getFamilyIdByUsername(principal.getName());
        return bookDao.getFamilyReadingListByCompletion(familyId, completed);
    }

    @RequestMapping(value = ENDPOINT + "/users", method = RequestMethod.GET)
    public List<User> getFamilyMembers(Principal principal){
        return null;
    }
}
