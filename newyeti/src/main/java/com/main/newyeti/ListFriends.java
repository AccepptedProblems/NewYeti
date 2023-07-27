package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.main.newyeti.model.ChatAdapter;
import com.main.newyeti.model.User;
import com.main.newyeti.model.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFriends extends AppCompatActivity {

    RecyclerView userView;
    UserAdapter userAdapter;
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

        userView = findViewById(R.id.listFriendsView);
        userAdapter = new UserAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        userView.setLayoutManager(linearLayoutManager);
        userAdapter.setListUser(getListUser());
        userView.setAdapter(userAdapter);

    }

    private List<User> getListUser(){
        List<User> list = new ArrayList<>();
        list.add(new User(R.drawable.avatar, "User name 1"));
        list.add(new User(R.drawable.avatar, "User name 2"));
        list.add(new User(R.drawable.avatar, "User name 3"));
        list.add(new User(R.drawable.avatar, "User name 4"));
        list.add(new User(R.drawable.avatar, "User name 5"));
        list.add(new User(R.drawable.avatar, "User name 6"));
        list.add(new User(R.drawable.avatar, "User name 1"));
        list.add(new User(R.drawable.avatar, "User name 2"));
        list.add(new User(R.drawable.avatar, "User name 3"));
        list.add(new User(R.drawable.avatar, "User name 4"));
        list.add(new User(R.drawable.avatar, "User name 5"));
        list.add(new User(R.drawable.avatar, "User name 6"));
        list.add(new User(R.drawable.avatar, "User name 1"));
        list.add(new User(R.drawable.avatar, "User name 2"));
        list.add(new User(R.drawable.avatar, "User name 3"));
        list.add(new User(R.drawable.avatar, "User name 4"));
        list.add(new User(R.drawable.avatar, "User name 5"));
        list.add(new User(R.drawable.avatar, "User name 6"));
        return list;
    }
}