package com.techelevator.controller;

import com.techelevator.dao.BookDao;
import com.techelevator.dao.FamilyDao;
import com.techelevator.dao.ReadingActivityDao;
import com.techelevator.model.Book;
import com.techelevator.model.Score;
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
    private ReadingActivityDao activityDao;
    private final String ENDPOINT = "/family";

    public FamilyController(FamilyDao familyDao, BookDao bookDao, ReadingActivityDao activityDao) {
        this.familyDao = familyDao;
        this.bookDao = bookDao;
        this.activityDao = activityDao;
    }

    @RequestMapping(value = "/members/{id}/books", method = RequestMethod.GET)
    public int getFamilyBooksFinished(@PathVariable int id) {
        return bookDao.getFamilyBooksFinished(id);
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

    @RequestMapping(value = "user/members/{id}/activity", method = RequestMethod.GET)
    public int getFamilyReadingActivityTotal(@PathVariable int id) {
        return activityDao.getTotalMinutesPerFamily(id);
    }

    @RequestMapping(value = "user/leaderboard/{id}", method = RequestMethod.GET)
    public List<String> getLeaderboard(@PathVariable int id) {
        return activityDao.getLeaderboard(id);
    }
}
