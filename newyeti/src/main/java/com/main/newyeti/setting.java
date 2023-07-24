package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class setting extends AppCompatActivity {

    private Button listChats, listFriends, setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        listChats = findViewById(R.id.listChats);
        listFriends = findViewById(R.id.listFriends);
        setting = findViewById(R.id.setting);

        listFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, listFriends.class);
                startActivity(intent);
                finish();
            }
        });

        listChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}