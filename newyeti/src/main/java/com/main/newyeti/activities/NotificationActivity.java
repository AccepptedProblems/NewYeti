package com.main.newyeti.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.main.newyeti.R;
import com.main.newyeti.adapter.NotificationAdapter;
import com.main.newyeti.model.Friend;
import com.main.newyeti.model.Notification;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    NotificationAdapter notificationAdapter;
    RecyclerView notificationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationView = findViewById(R.id.listNotification);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        notificationView.setLayoutManager(linearLayoutManager);
        notificationView.addItemDecoration(
                new DividerItemDecoration(this, RecyclerView.VERTICAL));

        notificationAdapter = new NotificationAdapter(this);
        getListNotification();

        ImageView arrowBack = findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(v -> {

            finish();
        });

    }

    private void getListNotification() {

        String id = DataLocalManager.getMyUserId();
        ApiService.apiService.getListFriendRequest(id).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(@NonNull Call<List<Notification>> call, @NonNull Response<List<Notification>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.e("MyLog", "ListFriendsRequest:getListFriendRequest: onResponse: " + response.body());
                    List<Notification> friendListRequest = response.body();
                    if (friendListRequest.size() > 0) {
                        notificationAdapter.setListNotifications(friendListRequest);
                        notificationView.setAdapter(notificationAdapter);
//                        Toast.makeText(this, "Có " + list.size() + " bạn bè", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("MyLog", "ListFriendsRequest:getListFriendRequest: onFailure: response.body() null");
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Notification>> call, @NonNull Throwable t) {
                Log.e("MyLog", "ListFriendsRequest:getListRequest: onFailure: " + t.getMessage());
//                Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}