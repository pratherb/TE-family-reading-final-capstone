package com.techelevator.dao;

import com.techelevator.model.Family;

import java.util.List;

public interface FamilyDao {

    List<Family> findAll();
    Family getFamilyById(int id);
    int create(Family family);
    Family findFamilyByEmail(String email);

}
