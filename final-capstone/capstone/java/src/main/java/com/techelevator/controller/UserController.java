package com.techelevator.controller;

import com.techelevator.dao.FamilyDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    public User createFamilyMember(@RequestBody User user, Principal principal) {
        int familyId = familyDao.getFamilyIdByUsername(principal.getName());
        int newUserId = userDao.create(
                user.getUsername(), user.getPassword(), "child",
                user.getFirstName(), user.getLastName(), familyId
        );
        return userDao.getUserById(newUserId);
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
    public List<User> getUsersByFamilyId(Principal principal){
        int id = familyDao.getFamilyIdByUsername(principal.getName());
        return userDao.getUsersByFamilyId(id);
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
    public void deleteUserByUsername(String username, Principal principal){
        if (principal.getName().equals(username)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete current user");
        } else {
            userDao.delete(username);
        }
    }

}
