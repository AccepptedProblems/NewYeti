package com.main.newyeti.model;

public class LoginResp {
    private User user;
    private String apiKey;

    public LoginResp(User user, String apiKey) {
        this.user = user;
        this.apiKey = apiKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
