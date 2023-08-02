package com.main.newyeti.utilities;

import android.content.Context;
import android.content.SharedPreferences;

class MySharedPreferences {

    private static final String MY_SHARED_PREFERENCES_STRING_VALUE = "MY_SHARED_PREFERENCES_STRING_VALUE";
    private static final String MY_SHARED_PREFERENCES_CLEAR = "MY_SHARED_PREFERENCES_CLEAR";
    private final Context mContext;

    public MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }

    public void putStringValue(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES_STRING_VALUE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES_STRING_VALUE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void clear() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES_CLEAR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
