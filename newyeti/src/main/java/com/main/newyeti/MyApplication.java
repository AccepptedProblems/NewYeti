package com.main.newyeti;

import android.app.Application;

import com.main.newyeti.utilities.DataLocalManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
