package com.main.newyeti;

import android.content.Context;

public class DataLocalManager {

    private static final String API_KEY = "API_KEY";
    private static final String USER_ID = "USER_ID";
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

    public static void setApiKey(String apiKey) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(API_KEY, apiKey);
    }

    public static String getApiKey() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(API_KEY);
    }

    public static void setMyUserId(String apiKey) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(USER_ID, apiKey);
    }

    public static String getMyUserId() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(USER_ID);
    }

}
