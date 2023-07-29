package com.main.newyeti.model;

public class Token {
    private String apiKey;

    public Token(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "Token{" +
                "apiKey='" + apiKey + '\'' +
                '}';
    }
}
