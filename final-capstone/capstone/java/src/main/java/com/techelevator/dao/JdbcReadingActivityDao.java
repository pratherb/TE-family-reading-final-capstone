package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.ReadingActivity;
import com.techelevator.model.Score;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.awt.datatransfer.SystemFlavorMap;
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
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, readingActivityId);
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
        String sql = "SELECT * FROM reading_activity WHERE username = ?";
        List<ReadingActivity> readingActivityList = new ArrayList<>();
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
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
    public ReadingActivity create(ReadingActivity readingActivity) {
        String sql = "INSERT INTO reading_activity" +
                "(username, book_isbn, minutes_read, format, notes)" +
                "VALUES (?,?,?,?,?) RETURNING activity_id";
        int activityId = 0;
        try {
            //First we need to add a book entry to the database, due to FK constraints
            //Get a book from Open Library using the readingActivity ISBN
            Book book = bookDao.searchBookByIsbn(readingActivity.getBookIsbn());
            bookDao.createBook(book);
            activityId = jdbcTemplate.queryForObject(sql, Integer.class,
                    readingActivity.getUsername(), book.getIsbn(), readingActivity.getMinutesRead(),
                    readingActivity.getFormat(), readingActivity.getNotes());
            ReadingActivity newActivity = getById(activityId);
            return newActivity;
        } catch (DataAccessException e) {
            System.out.println("Error creating reading activity.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ReadingActivity update(ReadingActivity readingActivity) {
        String sql = "UPDATE reading_activity SET" +
                "username = ?, book_isbn = ?, minutes_read = ?, format = ?, notes = ?" +
                "WHERE activity_id = ?";
        try {
            jdbcTemplate.update(sql,
                    readingActivity.getUsername(), readingActivity.getBookIsbn(), readingActivity.getMinutesRead(),
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
    public void deleteAllByUsername(String username) {
        //TBD: Call delete methods from other DAOs as necessary
        String sql = "DELETE FROM reading_activity WHERE username = ?";
        try{
            jdbcTemplate.update(sql, username);
        } catch (DataAccessException e){
            System.out.println("Error deleting user's reading activities.");
        }
    }

    @Override
    public int getTotalMinutesPerUser(String username) {
        int numMinutes = 0;
        String sql = "SELECT SUM(minutes_read) AS total_minutes FROM reading_activity WHERE username = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
            if (result.next()) {
                numMinutes = result.getInt("total_minutes");
            }
            return numMinutes;
        } catch (DataAccessException e) {
            System.out.println("Error retrieving reading total for user " + username);
        }
        return -1;
    }

    @Override
    public int getTotalMinutesPerFamily(int id) {
        int numMinutes = 0;
        String sql = "SELECT SUM(minutes_read) AS total_minutes FROM reading_activity a\n" +
                "JOIN users u ON u.username = a.username\n" +
                "JOIN family f ON f.family_id = u.family_id\n" +
                "WHERE f.family_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()) {
                numMinutes = result.getInt("total_minutes");
            }
            return numMinutes;
        } catch (DataAccessException e) {
            System.out.println("Error retrieving reading total for family");
        }
        return -1;
    }

    @Override
    public List<String> getLeaderboard(int id) {
        List<String> scores = new ArrayList<>();
        String sql = "SELECT a.username, SUM(minutes_read) AS score FROM reading_activity a\n" +
                "JOIN users u ON u.username = a.username\n" +
                "JOIN family f ON f.family_id = u.family_id\n" +
                "WHERE f.family_id = ? GROUP BY a.username ORDER BY score DESC";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                Score score = new Score();
                score.setMinutes(results.getInt("score"));
                score.setUsername(results.getString("username"));
                String scoreString = score.toString();
                scores.add(scoreString);
            }
            return scores;
        } catch (DataAccessException e) {
            System.out.println("Error retrieving leaderboard");
        }
        return null;
    }

    private ReadingActivity mapRowToReadingActivity(SqlRowSet rs) {
        ReadingActivity readingActivity = new ReadingActivity();
        readingActivity.setId(rs.getInt("activity_id"));
        readingActivity.setUsername(rs.getString("username"));
        readingActivity.setBookIsbn(rs.getString("book_isbn"));
        readingActivity.setMinutesRead(rs.getInt("minutes_read"));
        readingActivity.setFormat(rs.getString("format"));
        readingActivity.setNotes(rs.getString("notes"));
        return readingActivity;
    }
}
