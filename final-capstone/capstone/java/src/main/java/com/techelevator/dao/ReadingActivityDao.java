package com.techelevator.dao;

import com.techelevator.model.ReadingActivity;

import java.security.Principal;
import java.util.List;

public interface ReadingActivityDao {

    List<ReadingActivity> findAllByUsername(String username);
    List<ReadingActivity> findAll();
    ReadingActivity getById(int readingActivityId);
    ReadingActivity create(ReadingActivity readingActivity);
    ReadingActivity update(ReadingActivity readingActivity);
    void deleteById(int readingActivityId);
    void deleteAllByUsername(String username);
}
