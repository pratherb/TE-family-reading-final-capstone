package com.techelevator.dao;

import com.techelevator.model.Prize;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.xml.crypto.Data;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@CrossOrigin
public class JdbcPrizeDao implements PrizeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPrizeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Prize getById(int prizeId) {
        String sql = "SELECT * FROM prize\n" +
                "WHERE prize_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, prizeId);
            if (results.next()) {
                return mapRowToPrize(results);
            }
        } catch (DataAccessException e) {
            System.out.println("Error getting prize with id " + prizeId);
        }
        return null;
    }

    @Override
    public Prize getByName(String name) {
        String sql = "SELECT * FROM prize\n" +
                "WHERE name = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
            if (results.next()) {
                return mapRowToPrize(results);
            }

        } catch (DataAccessException e) {
            System.out.println("Error getting prize named " + name);
        }
        return null;
    }

    @Override
    public List<Prize> getUserPrizesByUserId(int userId) {
        List<Prize> prizeList = new ArrayList<>();
        String sql = "SELECT * FROM prize\n" +
                "WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Prize prize = mapRowToPrize(results);
                prizeList.add(prize);
            }
            return prizeList;
        } catch (DataAccessException e) {
            System.out.println("Error getting prizes for user with id " + userId);
        }
        return null;
    }

    @Override
    public List<Prize> getUserPrizesByUsername(String username) {
        List<Prize> prizeList = new ArrayList<>();
        String sql = "SELECT * FROM prize p\n" +
                "JOIN users u ON u.user_id = p.user_id\n" +
                "WHERE u.username = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            while (results.next()) {
                Prize prize = mapRowToPrize(results);
                prizeList.add(prize);
            }
            return prizeList;
        } catch (DataAccessException e) {
            System.out.println("Error getting prizes for user " + username);
        }
        return null;
    }

    @Override
    //TBD - this isn't finished
    //Get prizes by user group (parent/children/both) depending on principal
    public List<Prize> getPrizesByUserGroup(String userGroup, Principal principal) {
        List<Prize> prizeList = new ArrayList<>();
        String sql = "SELECT * FROM prize\n" +
                "WHERE user_group = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userGroup);
            while (results.next()) {
                Prize prize = mapRowToPrize(results);
                prizeList.add(prize);
            }
            return prizeList;
        } catch (DataAccessException e) {
            System.out.println("Error getting prizes for group " + userGroup);
        }
        return null;
    }

    //This needs Principal too - we don't need to see prizes for all family accounts
    @Override
    public List<Prize> getPrizesBetweenDates(LocalDate date1, LocalDate date2) {
        return null;
    }

    @Override
    public Prize create(Prize prize) {
        Prize newPrize;
        String sql = "INSERT INTO prize\n" +
                "(user_id, name, description, milestone, user_group, start_date, end_date)\n" +
                "VALUES(?,?,?,?,?,?,?)\n" +
                "RETURNING prize_id";
        try {
            int id = jdbcTemplate.update(sql,
                    prize.getUserId(), prize.getName(), prize.getDescription(), prize.getMilestone(),
                    prize.getUserGroup(), prize.getStartDate(), prize.getEndDate());
            return getById(id);

        } catch (DataAccessException e) {
            System.out.println("Error creating prize.");
        }
        return null;
    }

    @Override
    public Prize update(Prize prize) {
        int prizeId = -1;
        String sql = "UPDATE prize\n"+
                "SET user_id = ?, name = ?, description = ?, milestone = ? "+
                "user_group = ?, start_date = ?, end_date = ?\n"+
                "WHERE prize_id = ?";
        try{
            //prizeId = getByName()

        } catch (DataAccessException e){
            System.out.println("Error updating prize of id ");
        }
        return null;
    }

    @Override
    public void delete(Prize prize) {
    }

    private Prize mapRowToPrize(SqlRowSet rs) {
        Prize prize = new Prize();
        String defaultDate = "1-1-1900";
        //Set 2 default values here, in case DB doesn't specify dates
        prize.setStartDate(LocalDate.parse(defaultDate));
        prize.setEndDate(LocalDate.parse(defaultDate));
        try {
            prize.setEndDate(null);
            prize.setUserId(rs.getInt("user_id"));
            prize.setName(rs.getString("name"));
            prize.setDescription(rs.getString("description"));
            prize.setMilestone(rs.getInt("milestone"));
            prize.setUserGroup(rs.getString("user_group"));
            prize.setStartDate(rs.getDate("start_date").toLocalDate());
            prize.setEndDate(rs.getDate("end_date").toLocalDate());
        } catch (NullPointerException e) {
            System.out.println("Couldn't get date from prize.");
        }
        return prize;
    }
}
