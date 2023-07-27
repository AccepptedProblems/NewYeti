package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LogoutDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_dialog);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}