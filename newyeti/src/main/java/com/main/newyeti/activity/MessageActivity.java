package com.main.newyeti.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.R;

public class MessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }
}