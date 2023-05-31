package com.techelevator.dao;

import com.techelevator.model.Family;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFamilyDao implements FamilyDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcFamilyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Family> findAll() {
        List<Family> families = new ArrayList<>();
        String sql = "select * from family";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Family family = mapRowToFamily(results);
            families.add(family);
        }
        return families;
    }

    @Override
    public Family getFamilyById(int id) {
        String sql = "SELECT * FROM family WHERE family_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToFamily(results);
        } else {
            return null;
        }
    }

    @Override
    public int getFamilyIdByUsername(String username){
        String sql = "SELECT family.family_id FROM family JOIN users\n" +
                "ON users.family_id = family.family_id WHERE username = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
        if(result.next()){
            return result.getInt("family_id");
        }
        return -1;
    }

    @Override
    public int create(Family family) {
        String insertFamilySql = "insert into family (name, email_address) values (?,?)";
        int result = jdbcTemplate.update(insertFamilySql, family.getName(), family.getEmail());
        if (result == 1) {
            int familyId;
            try {
                familyId = jdbcTemplate.queryForObject("SELECT family_id FROM family WHERE email_address = ?", Integer.class, family.getEmail());
                return familyId;
            } catch (EmptyResultDataAccessException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        return -1;
    }

    @Override
    public Family findFamilyByEmail(String email){
        if (email == null) throw new IllegalArgumentException("Email cannot be null");

        for (Family family : this.findAll()) {
            if (family.getEmail().equalsIgnoreCase(email)) {
                return family;
            }
        }
        throw new UsernameNotFoundException("Family with address " + email + " was not found.");
    }

    private Family mapRowToFamily(SqlRowSet rs) {
        Family family = new Family();
        family.setFamilyId(rs.getInt("family_id"));
        family.setName(rs.getString("name"));
        family.setEmail(rs.getString("email_address"));
        return family;
    }
}
