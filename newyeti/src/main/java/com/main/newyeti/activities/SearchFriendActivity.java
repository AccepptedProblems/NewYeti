package com.main.newyeti.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    private ProgressBar progressBar;
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
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.search);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        searchListView.setLayoutManager(linearLayoutManager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListUser();
    }

    private void getListUser() {
        List<User> list = new ArrayList<>();

        String header = "Bearer " + DataLocalManager.getApiKey();

        loading(true);

        ApiService.apiService.getListUsers(header).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.e("SearchFriendActivity", "onResponse: " + response.body().size());
                    List<User> userLists = response.body();
                    for (User user : userLists) {
                        user.setResourceAvt(R.drawable.avatar);
                        if (!user.getEmail().equals(DataLocalManager.getMyEmail()))
                            list.add(user);
                    }
                } else {
                    Log.e("SearchFriendActivity", "onResponse: " + response.code());
                    list.add(new User(R.drawable.avatar, "Lỗi"));
                }

                if (list.size() > 0) {
                    searchListAdapter.setListUser(list);
                    searchListView.setAdapter(searchListAdapter);
                }
                loading(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                loading(false);
                Log.e("SearchFriendActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    private void loading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            searchListView.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            searchListView.setVisibility(View.VISIBLE);
        }
    }
}