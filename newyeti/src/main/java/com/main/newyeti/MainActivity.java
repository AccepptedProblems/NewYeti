package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listChatView;
    private RecyclerView.Adapter listChatAdapter;
    private Button listChats, listFriends, setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        listChats = findViewById(R.id.listChats);
        listFriends = findViewById(R.id.listFriends);
        setting = findViewById(R.id.setting);

        listFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListFriends.class);
                startActivity(intent);
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });

        listChatView = findViewById(R.id.listChatView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
    }
}