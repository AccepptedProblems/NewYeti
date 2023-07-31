package com.main.newyeti.utilities;

import android.content.Context;

public class DataLocalManager {
    private static final String API_KEY = "API_KEY";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_NAME = "KEY_NAME";
    private static final String KEY_EMAIL = "KEY_EMAIL";
    private static final String KEY_PASSWORD = "KEY_PASSWORD";

    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static String getApiKey() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(API_KEY);
    }

    public static void setApiKey(String apiKey) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(API_KEY, apiKey);
    }

    public static String getMyUserId() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(KEY_USER_ID);
    }

    public static void setMyUserId(String id) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(KEY_USER_ID, id);
    }

    public static String getMyName() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(KEY_NAME);
    }

    public static void setMyName(String name) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(KEY_NAME, name);
    }

    public static String getMyEmail() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(KEY_EMAIL);
    }

    public static void setMyEmail(String email) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(KEY_EMAIL, email);
    }

    public static String getMyPassword() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(KEY_PASSWORD);
    }

    public static void setMyPassword(String password) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(KEY_PASSWORD, password);
    }
}
