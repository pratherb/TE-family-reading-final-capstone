package com.techelevator.controller;

import com.techelevator.dao.FamilyDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;
    private FamilyDao familyDao;
    private final String ENDPOINT = "/user";

    public UserController(UserDao userDao, FamilyDao familyDao) {
        this.userDao = userDao;
        this.familyDao = familyDao;
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public User createFamilyMember(User user, Principal principal){
        int familyId = familyDao.getFamilyIdByUsername(principal.getName());
        int newUserId = userDao.create(
                user.getUsername(), user.getPassword(), "Parent",
                user.getFirstName(), user.getLastName(), familyId
        );
        return userDao.getUserById(newUserId);
    }
}
