package com.main.newyeti.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.adapter.UserAdapter;
import com.main.newyeti.model.User;

import java.util.ArrayList;
import java.util.List;

public class SearchFriendActivity extends AppCompatActivity {
    RecyclerView searchListView;
    UserAdapter searchListAdapter;
    TextView cancelSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        cancelSearch = findViewById(R.id.cancelSearch);

        cancelSearch.setOnClickListener(v -> {
            Intent intent = new Intent(SearchFriendActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        searchListView = findViewById(R.id.listSearchView);
        searchListAdapter = new UserAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        searchListView.setLayoutManager(linearLayoutManager);
        searchListAdapter.setListUser(getListUser());
        searchListView.setAdapter(searchListAdapter);
    }

    private List<User> getListUser() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User(R.drawable.avatar, "User name " + i));
        }
        return list;
    }
}