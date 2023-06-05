package com.techelevator.controller;

import com.techelevator.dao.ReadingActivityDao;
import com.techelevator.model.ReadingActivity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
public class ReadingActivityController {

    private ReadingActivityDao readingActivityDao;
    private final String ENDPOINT = "/user/{username}/activity";

    public ReadingActivityController(ReadingActivityDao readingActivityDao) {
        this.readingActivityDao = readingActivityDao;
    }

    @RequestMapping(value = ENDPOINT + "/all", method = RequestMethod.GET)
    public List<ReadingActivity> findAll() {
        return readingActivityDao.findAll();
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
    public List<ReadingActivity> findAllByUsername(@RequestParam String username) {
        return readingActivityDao.findAllByUsername(username);
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public ReadingActivity create(@RequestBody ReadingActivity readingActivity, @RequestParam String username, Principal principal){
        return readingActivityDao.create(readingActivity, username, principal);
    }
}
