package com.techelevator.dao;

import com.techelevator.model.ReadingActivity;

import java.util.List;

public interface ReadingActivityDao {

    List<ReadingActivity> findAllByUserId(int userId);
    List<ReadingActivity> findAll();
    ReadingActivity getById(int readingActivityId);
    int create(ReadingActivity readingActivity);
    ReadingActivity update(ReadingActivity readingActivity);
    ReadingActivity deleteById(int readingActivityId);
    ReadingActivity deleteAllByUserId(int userId);
}
