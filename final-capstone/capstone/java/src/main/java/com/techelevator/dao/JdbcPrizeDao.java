package com.techelevator.dao;

import com.techelevator.model.Prize;
import org.springframework.dao.DataAccessException;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@CrossOrigin
public class JdbcPrizeDao implements PrizeDao {

    private final JdbcTemplate jdbcTemplate;
    private FamilyDao familyDao;
    private UserDao userDao;

    public JdbcPrizeDao(JdbcTemplate jdbcTemplate, FamilyDao familyDao, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.familyDao = familyDao;
        this.userDao = userDao;
    }

    //For quick testing
    @Override
    public List<Prize> findAll() {
        List<Prize> prizeList = new ArrayList<>();
        String sql = "SELECT * FROM prize";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Prize prize = mapRowToPrize(results);
                prizeList.add(prize);
            }
            return prizeList;
        } catch (DataAccessException e) {
            System.out.println("Error fetching all prizes.");
        }
        return null;
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
    public int getIdByName(String name) {
        String sql = "SELECT * FROM prize\n" +
                "WHERE name = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
            if (results.next()) {
                return results.getInt("prize_id");
            }

        } catch (DataAccessException e) {
            System.out.println("Error getting prize named " + name);
        }
        return -1;
    }

    @Override
    public List<Prize> getTrackedUserPrizesByUserId(int userId) {
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
    public List<Prize> getTrackedUserPrizeByUsername(String username) {
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
    //Get prizes by user group (parent/children/both) for logged in family member
    public List<Prize> getPrizesByUserGroup(String userGroup, Principal principal) {
        List<Prize> prizeList = new ArrayList<>();
        String sql = "SELECT * FROM prize\n" +
                "WHERE user_group ILIKE ?\n" +
                "AND family_id = ?";
        try {
            int familyId = familyDao.getFamilyIdByUsername(principal.getName());
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userGroup, familyId);
            while (results.next()) {
                Prize prize = mapRowToPrize(results);
                prizeList.add(prize);
            }
            System.out.println("Accessed a prize list of length " + prizeList.size());
            return prizeList;
        } catch (DataAccessException e) {
            System.out.println("Error getting prizes for group " + userGroup);
        }
        return null;
    }

    //This needs Principal too - we don't need to see prizes for all family accounts
    //Do this later - may not be needed
    @Override
    public List<Prize> getPrizesBetweenDates(LocalDate date1, LocalDate date2) {
        return null;
    }

    //Get all prizes associated with a family
    @Override
    public List<Prize> getPrizesByEmailAddress(String emailAddress) {
        List<Prize> prizeList = new ArrayList<>();
        String sql = "SELECT * FROM prize p\n" +
                "JOIN family f ON f.family_id = p.family_id\n" +
                "WHERE f.email_address = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, emailAddress);
            while (results.next()) {
                Prize prize = mapRowToPrize(results);
                prizeList.add(prize);
            }
            return prizeList;
        } catch (DataAccessException e) {
            System.out.println("Error getting prizes by email " + emailAddress);
        }
        return null;
    }

    @Override
    //Probably not needed..
    public List<Prize> getPrizesByFamilyId(int familyId) {
        return null;
    }

    @Override
    public Prize create(Prize prize, Principal principal) {
        Prize newPrize;
        //Inserts NULL into user_id initially - this gets filled when a user is "tracking" it
        String sql = "INSERT INTO prize\n" +
                "(family_id, name, description, milestone, max_prizes, user_group, start_date, end_date)\n" +
                "VALUES(?,?,?,?,?,?,?,?)\n" +
                "RETURNING prize_id";
        try {
            int familyId;
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusYears(1);
            familyId = familyDao.getFamilyIdByUsername(principal.getName());
            int id = jdbcTemplate.queryForObject(sql, Integer.class,
                    familyId, prize.getName(), prize.getDescription(), prize.getMilestone(),
                    prize.getMaxPrizes(), prize.getUserGroup(), startDate, endDate);
            return getById(id);

        } catch (DataAccessException e) {
            System.out.println("Error creating prize.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Prize update(Prize prize) {
        int prizeId = -1;
        String sql = "UPDATE prize\n" +
                "SET user_id = ?, family_id = ?, name = ?, description = ?, milestone = ? " +
                "max_prizes = ? user_group = ?, start_date = ?, end_date = ?\n" +
                "WHERE prize_id = ?\n" +
                "RETURNING prize_id";
        try {
            prizeId = getIdByName(prize.getName());
            int updatedPrizeId = jdbcTemplate.update(sql,
                    prize.getUserId(), prize.getFamilyId(), prize.getName(), prize.getDescription(), prize.getMaxPrizes(),
                    prize.getMilestone(), prize.getUserGroup(), prize.getStartDate(), prize.getEndDate(),
                    prizeId
            );
            return getById(updatedPrizeId);
        } catch (DataAccessException e) {
            System.out.println("Error updating prize of id ");
        }
        return null;
    }

    @Override
    public void deleteByName(String prizeName) {
        String sql = "DELETE FROM prize\n" +
                "WHERE name = ?";
        try {
            jdbcTemplate.update(sql, prizeName);
        } catch (DataAccessException e) {
            System.out.println("Error deleting prize " + prizeName);
        }
    }

    private Prize mapRowToPrize(SqlRowSet rs) {
        Prize prize = new Prize();
        try {
            prize.setEndDate(null);
            prize.setUserId(rs.getInt("user_id"));
            prize.setFamilyId(rs.getInt("family_id"));
            prize.setName(rs.getString("name"));
            prize.setDescription(rs.getString("description"));
            prize.setMilestone(rs.getInt("milestone"));
            prize.setMaxPrizes(rs.getInt("max_prizes"));
            prize.setUserGroup(rs.getString("user_group").toLowerCase());
            prize.setStartDate(rs.getDate("start_date").toLocalDate());
            prize.setEndDate(rs.getDate("end_date").toLocalDate());
        } catch (NullPointerException e) {
            System.out.println("Couldn't get date from prize.");
        }
        return prize;
    }

    //Returns a user_prize in JSON data, for testing - doesn't need to actually return anything otherwise
    public void awardPrizeByUser(String prizeName, String username) {
        String sql = "INSERT INTO user_prize\n" +
                "(user_id, prize_id, won_date)\n" +
                "VALUES(?,?,?)\n";
        try {
            int prizeId = getIdByName(prizeName);
            int userId = userDao.findByUsername(username).getId();
            int result = jdbcTemplate.update(sql,
                    userId, prizeId, LocalDate.now());
        } catch (DataAccessException e) {
            System.out.println("Error awarding a prize to " + username);
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Prize> getPrizesWonByUser(String username) {
        List<Prize> prizeList = new ArrayList<>();
        String sql = "SELECT * from prize p\n" +
                "JOIN user_prize up ON up.prize_id = p.prize_id\n" +
                "WHERE up.user_id = ?";
        try {
            int userId = userDao.findByUsername(username).getId();
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Prize prize = mapRowToPrize(results);
                prizeList.add(prize);
            }
            return prizeList;
        } catch (DataAccessException e) {
            System.out.println("Error getting prizes awarded to user " + username);
        }
        return null;
    }
}
