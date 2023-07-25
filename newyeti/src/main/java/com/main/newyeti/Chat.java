package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}