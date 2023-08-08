package com.main.newyeti.model;

import java.util.Date;

public class UserRegister {
    private String username;
    private String password;
    private String email;
    private String displayName;
    private String gender;
    private Date dayOfBirth;

    public UserRegister(String username, String password, String email, String displayName, String gender, Date dayOfBirth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.displayName = displayName;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;
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
        if (gender == "MALE") {
            return "Name";
        }
        return "Nữ";
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
