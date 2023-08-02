package com.main.newyeti.model;

import java.util.Date;

public class User {
    private int resourceAvt;
    private String id;
    private String username;
    private String password;
    private String email;
    private String displayName;
    private String gender;
    private Date dayOfBirth;

    public User() {
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int resourceAvt, String username) {
        this.resourceAvt = resourceAvt;
        this.username = username;
    }

    public User(String username, String password, String email, String displayName, String gender, Date dayOfBirth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.displayName = displayName;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;
    }

    public User(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.displayName = user.getDisplayName();
        this.gender = user.getGender();
        this.dayOfBirth = user.getDayOfBirth();
    }

    public User(String id, String username, String password, String email, String displayName, String gender, Date dayOfBirth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.displayName = displayName;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;
    }

    public int getResourceAvt() {
        return resourceAvt;
    }

    public void setResourceAvt(int resourceAvt) {
        this.resourceAvt = resourceAvt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }
}
