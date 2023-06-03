package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

import javax.validation.constraints.Null;
import javax.xml.crypto.Data;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        String sql = "SELECT user_id FROM users WHERE lower(username) = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, username.toLowerCase());
        } catch (DataAccessException | NullPointerException e) {
            System.out.println("User " + username + " was not found.");
        }
        return -1;
    }

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			return mapRowToUser(results);
		} else {
			return null;
		}
	}

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public List<User> getUsersByFamilyId(int id) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE family_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        while (results.next()){
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public int create(String username, String password, String role, String firstName, String lastName, int familyId) {
        String insertUserSql = "insert into users (family_id, username,password_hash,role, first_name, last_name) values (?,?,?,?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();
        int result = jdbcTemplate.update(insertUserSql, familyId, username, password_hash, ssRole, firstName, lastName);
        if (result == 1){
            return findIdByUsername(username);
        }
        return -1;
    }

    @Override
    public void delete(String username){
        String sql = "DELETE FROM users WHERE username = ?";
        try {
            jdbcTemplate.update(sql, username);
        } catch(CannotGetJdbcConnectionException e){
            throw new RuntimeException("Cannot connect to database", e);
        }catch(BadSqlGrammarException e){
            throw new RuntimeException("Syntax error", e);
        }catch(DataIntegrityViolationException e){
            throw new RuntimeException("Data integrity violation, delete not completed", e);
        }
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setFamilyId(rs.getInt("family_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setActivated(true);
        return user;
    }
}
