package com.techelevator.dao;

import com.techelevator.model.Prize;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface PrizeDao {

    Prize getById(int prizeId);
    Prize getByName(String name);
    List<Prize> getUserPrizesByUserId(int userId);
    List<Prize> getUserPrizesByUsername(String username);
    List<Prize> getPrizesByUserGroup(String userGroup, Principal principal);
    List<Prize> getPrizesBetweenDates(LocalDate date1, LocalDate date2);

    Prize create(Prize prize);
    Prize update(Prize prize);
    void delete(Prize prize);

}