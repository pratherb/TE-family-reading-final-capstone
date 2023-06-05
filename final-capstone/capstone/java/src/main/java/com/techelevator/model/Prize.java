package com.techelevator.model;

import java.time.LocalDate;

public class Prize {
    private String name;
    private String description;
    private int milestone; //Minutes to read to reach this prize
    private String userGroup; //Parent, child, both
    private int maxPrizes;
    private LocalDate startDate;
    private LocalDate endDate;
    private int userId = -1; //userId exists if it's being tracked by a user
    private int familyId; //Not null - family that "owns" this prize

    public Prize(String name, String description, int milestone, String userGroup, int maxPrizes, LocalDate startDate, LocalDate endDate, int userId, int familyId) {
        this.name = name;
        this.description = description;
        this.milestone = milestone;
        this.userGroup = userGroup;
        this.maxPrizes = maxPrizes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.familyId = familyId;
    }

    public Prize() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMilestone() {
        return milestone;
    }

    public void setMilestone(int milestone) {
        this.milestone = milestone;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public int getMaxPrizes() {
        return maxPrizes;
    }

    public void setMaxPrizes(int maxPrizes) {
        this.maxPrizes = maxPrizes;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }
}
