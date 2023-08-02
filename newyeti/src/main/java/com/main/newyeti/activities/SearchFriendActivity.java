package com.main.newyeti.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.adapter.UserAdapter;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFriendActivity extends AppCompatActivity {
    private RecyclerView searchListView;
    private UserAdapter searchListAdapter;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        TextView cancelSearch = findViewById(R.id.cancelSearch);

        cancelSearch.setOnClickListener(v -> {
            Intent intent = new Intent(SearchFriendActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        searchListView = findViewById(R.id.listSearchView);
        searchListAdapter = new UserAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        searchListView.setLayoutManager(linearLayoutManager);
        getListUser();
//        searchListAdapter.setListUser(getListUser());
//        searchListView.setAdapter(searchListAdapter);


    }

    private void getListUser() {
        List<User> list = new ArrayList<>();

        String header = "Bearer " + DataLocalManager.getApiKey();
        ApiService.apiService.getListUsers(header).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    List<User> userLists = response.body();
                    for (User user : userLists) {
                        user.setResourceAvt(R.drawable.avatar);
                        list.add(user);
                    }
                } else {
                    list.add(new User(R.drawable.avatar, "Lỗi"));
                }
                if (list.size() > 0) {
                    searchListAdapter.setListUser(list);
                    searchListView.setAdapter(searchListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(SearchFriendActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}