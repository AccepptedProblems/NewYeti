package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.main.newyeti.model.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView chatView;
    private ChatAdapter chatAdapter;
    private Button listChats, listFriends, setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        listChats = findViewById(R.id.listChats);
        listFriends = findViewById(R.id.listFriends);
        setting = findViewById(R.id.setting);

        listFriends.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ListFriends.class);
            startActivity(intent);
            finish();
        });

        setting.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Setting.class);
            startActivity(intent);
            finish();
        });

        chatView = findViewById(R.id.listChatView);
        chatAdapter = new ChatAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        chatView.setLayoutManager(linearLayoutManager);
        chatAdapter.setListChat(getListChat());
        chatView.setAdapter(chatAdapter);

    }

    private List<Chat> getListChat(){
        List<Chat> list = new ArrayList<>();
        list.add(new Chat(R.drawable.avatar, "User name 1", "Hello!"));
        list.add(new Chat(R.drawable.avatar, "User name 2", "Hello!"));
        list.add(new Chat(R.drawable.avatar, "User name 3", "Hello!"));
        list.add(new Chat(R.drawable.avatar, "User name 4", "Hello!"));
        list.add(new Chat(R.drawable.avatar, "User name 5", "Hello!"));
        list.add(new Chat(R.drawable.avatar, "User name 6", "Hello!"));
        return list;
    }
}