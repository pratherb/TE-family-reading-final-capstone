package com.techelevator.dao;

import com.techelevator.model.Prize;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface PrizeDao {

    //For quick testing
    List<Prize> findAll();

    Prize getById(int prizeId);

    Prize getByName(String name);
    int getIdByName(String name);
    List<Prize> getTrackedUserPrizesByUserId(int userId);
    List<Prize> getTrackedUserPrizeByUsername(String username);
    List<Prize> getPrizesByUserGroup(String userGroup, Principal principal);
    List<Prize> getPrizesBetweenDates(LocalDate date1, LocalDate date2);
    List<Prize> getPrizesByFamilyId(int familyId);
    List<Prize> getPrizesByEmailAddress(String emailAddress);

    Prize create(Prize prize);
    Prize update(Prize prize);
    void deleteByName(String prizeName);

    //Returns a user_prize in JSON data, for testing - doesn't need to actually return anything otherwise
    void awardPrizeByUser(String prizeName, String username);
    List<Prize> getPrizesWonByUser(String username);
}
