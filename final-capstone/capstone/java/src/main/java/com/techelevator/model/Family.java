package com.techelevator.model;

public class Family {

    private int familyId;
    private String name;
    private String email;

    public Family(int familyId, String name, String email) {
        this.familyId = familyId;
        this.name = name;
        this.email = email;
    }

    public Family(String name, String email){
        this.name = name;
        this.email = email;
    }

    public Family(){

    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
