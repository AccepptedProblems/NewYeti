package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.main.newyeti.model.User;
import com.main.newyeti.model.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchList extends AppCompatActivity {

    RecyclerView searchListView;
    UserAdapter searchListAdapter;
    TextView cancelSearch;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        cancelSearch = findViewById(R.id.cancelSearch);

        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchList.this, ListFriends.class);
                startActivity(intent);
                finish();
            }
        });

        searchListView = findViewById(R.id.listSearchView);
        searchListAdapter = new UserAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        searchListView.setLayoutManager(linearLayoutManager);
        searchListAdapter.setListUser(getListUser());
        searchListView.setAdapter(searchListAdapter);

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