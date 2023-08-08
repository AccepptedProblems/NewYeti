package com.main.newyeti.model;

import com.main.newyeti.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private int resourceAvt = R.drawable.avatar;
    private String id;
    private String username;
    private String password;
    private String email;
    private String displayName;
    private String gender;
    private Date dayOfBirth;

    public User() {
    }

    public User(String id, String username, String email, String displayName, String gender, Date dayOfBirth) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDayOfBirthString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(dayOfBirth);
        return formattedDate;
    }
}
