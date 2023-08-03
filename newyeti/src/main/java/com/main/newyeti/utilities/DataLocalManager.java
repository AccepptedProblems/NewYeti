package com.main.newyeti.utilities;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.main.newyeti.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataLocalManager {
    public static final String KEY_ID_RECEIVER_USER = "KEY_ID_RECEIVER_USER";
    public static final String KEY_NAME_RECEIVER_USER = "KEY_NAME_USER_TO";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String API_KEY = "API_KEY";
    private static final String KEY_NAME = "KEY_NAME";
    private static final String KEY_EMAIL = "KEY_EMAIL";
    private static final String KEY_PASSWORD = "KEY_PASSWORD";
    private static final String KEY_OBJECT_USER = "KEY_OBJECT_USER";
    private static final String KEY_LIST_OBJECT_USER = "KEY_LIST_OBJECT_USER";
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

    public static User getUser() {
        Gson gson = new Gson();
        String strJsonUser = DataLocalManager.getInstance().mySharedPreferences.getStringValue(KEY_OBJECT_USER);
        User user = gson.fromJson(strJsonUser, User.class);
        return user;
    }

    public static void setUser(User user) {
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(KEY_OBJECT_USER, strJsonUser);
    }

    public static List<User> getListUsers() {
        String strJsonListUsers = DataLocalManager.getInstance().mySharedPreferences.getStringValue(KEY_LIST_OBJECT_USER);
        List<User> listUsers = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(strJsonListUsers);
            JSONObject jsonObject;
            User user;
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                user = gson.fromJson(jsonObject.toString(), User.class);
                listUsers.add(user);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }

    public static void setListUsers(List<User> listUsers) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(listUsers).getAsJsonArray();
        String strJsonListUsers = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(KEY_LIST_OBJECT_USER, strJsonListUsers);
    }

    public static void clear() {
        DataLocalManager.getInstance().mySharedPreferences.clear();
    }
}
