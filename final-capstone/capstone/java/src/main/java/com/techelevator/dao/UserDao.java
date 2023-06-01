package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    List<User> getUsersByFamilyId(int id);

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    int create(String username, String password, String role, String firstName, String lastName, int familyId);

    void delete(String username);
}
