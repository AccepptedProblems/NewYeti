package com.main.newyeti.model;

public class Notification {

    private String id;
    private User user;

    public Notification() {
    }

    public Notification(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
