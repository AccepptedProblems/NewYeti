package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListFriends extends AppCompatActivity {

    private Button listChats, listFriends, setting, searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friends);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        listChats = findViewById(R.id.listChats);
        listFriends = findViewById(R.id.listFriends);
        setting = findViewById(R.id.setting);
        searchList = findViewById(R.id.searchList);

        searchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListFriends.this, SearchList.class);
                startActivity(intent);
                finish();
            }
        });

        listChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListFriends.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListFriends.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });
    }
}