package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.ReadingActivity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReadingActivityDao implements ReadingActivityDao {

    private final JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private BookDao bookDao;

    public JdbcReadingActivityDao(JdbcTemplate jdbcTemplate, UserDao userDao, BookDao bookDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.bookDao = bookDao;
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
            return readingActivityList;
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
    public List<ReadingActivity> findAllByUsername(String username) {
        String sql = "SELECT * FROM reading_activity WHERE user_id = ?";
        List<ReadingActivity> readingActivityList = new ArrayList<>();
        int userId = userDao.findByUsername(username).getId();
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                ReadingActivity readingActivity = mapRowToReadingActivity(results);
                readingActivityList.add(readingActivity);
            }
            return readingActivityList;
        } catch(DataAccessException e){
            System.out.println("Error accessing reading activity list.");
        }
        return null;
    }

    @Override
    public ReadingActivity create(ReadingActivity readingActivity, Principal principal) {
        String sql = "INSERT INTO reading_activity" +
                "(user_id, book_isbn, minutes_read, format, notes)" +
                "VALUES (?,?,?,?,?)";
        try {
            //isbn is an INT in the database, but a String in Java - this fixes that
            int isbn = Integer.parseInt(readingActivity.getBookIsbn());
            //Find out the ID of the user recording this activity (or who is logged in)
            int userId = userDao.findByUsername(principal.getName()).getId();

            //First we need to add a book entry to the database, due to FK constraints
            //Get a book from Open Library using the readingActivity ISBN
            Book book = bookDao.searchBookByIsbn(readingActivity.getBookIsbn());
            bookDao.createBook(book);
            int result = jdbcTemplate.update(sql,
                    userId, isbn, readingActivity.getMinutesRead(),
                    readingActivity.getFormat(), readingActivity.getNotes());
            return readingActivity;

        } catch (DataAccessException e) {
            System.out.println("Error creating reading activity.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ReadingActivity update(ReadingActivity readingActivity) {
        String sql = "UPDATE reading_activity SET" +
                "user_id = ?, book_isbn = ?, minutes_read = ?, format = ?, notes = ?" +
                "WHERE activity_id = ?";
        try {
            jdbcTemplate.update(sql,
                    readingActivity.getUserId(), readingActivity.getBookIsbn(), readingActivity.getMinutesRead(),
                    readingActivity.getFormat(), readingActivity.getFormat(), readingActivity.getNotes(),
                    readingActivity.getId());
            return readingActivity;
        } catch (DataAccessException e) {
            System.out.println("Error updating reading activity.");
        }
        return null;
    }

    @Override
    public void deleteById(int readingActivityId) {
        //TBD: Call delete methods from other DAOs as necessary
        String sql = "DELETE FROM reading_activity WHERE activity_id = ?";
        try{
            jdbcTemplate.update(sql, readingActivityId);
        } catch(DataAccessException e){
            System.out.println("Error deleting reading activity.");
        }
    }

    @Override
    public void deleteAllByUserId(int userId) {
        //TBD: Call delete methods from other DAOs as necessary
        String sql = "DELETE FROM reading_activity WHERE user_id = ?";
        try{
            jdbcTemplate.update(sql, userId);
        } catch (DataAccessException e){
            System.out.println("Error deleting user's reading activities.");
        }
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
