package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.ReadingActivity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReadingActivityDao implements ReadingActivityDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReadingActivityDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReadingActivity> findAllByUserId(int userId) {
        return null;
    }

    @Override
    public List<ReadingActivity> findAll() {
        List<ReadingActivity> readingActivityList = new ArrayList<>();
        String sql = "SELECT * FROM reading_activity";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                ReadingActivity readingActivity = mapRowToReadingActivity(results);
                readingActivityList.add(readingActivity);
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing reading activity.");
        }
        return null;
    }

    @Override
    public ReadingActivity getById(int readingActivityId) {
        String sql = "SELECT * FROM reading_activity WHERE activity_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if (results.next()) {
                return mapRowToReadingActivity(results);
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing reading activity.");
        }
        return null;
    }

    @Override
    //Returns ID of new reading activity
    public int create(ReadingActivity readingActivity) {
        String sql = "INSERT INTO reading_activity" +
                "(user_id, book_isbn, minutes_read, format, notes)" +
                "VALUES (?,?,?,?,?)";
        try{
            jdbcTemplate.update(sql,
                    readingActivity.getUserId(), readingActivity.getBookIsbn(),readingActivity.getMinutesRead(),
                    readingActivity.getFormat(), readingActivity.getNotes());
            return 1;

        } catch(DataAccessException e){
            System.out.println("Error creating reading activity.");
        }
        return -1;
    }

    @Override
    public ReadingActivity update(ReadingActivity readingActivity) {
        return null;
    }

    @Override
    public ReadingActivity deleteById(int readingActivityId) {
        return null;
    }

    @Override
    public ReadingActivity deleteAllByUserId(int userId) {
        return null;
    }

    private ReadingActivity mapRowToReadingActivity(SqlRowSet rs) {
        ReadingActivity readingActivity = new ReadingActivity();
        readingActivity.setUserId(rs.getInt("user_id"));
        readingActivity.setBookIsbn(rs.getString("book_isbn"));
        readingActivity.setMinutesRead(rs.getInt("minutes_read"));
        readingActivity.setFormat(rs.getString("format"));
        readingActivity.setNotes(rs.getString("notes"));
        return readingActivity;
    }
}
