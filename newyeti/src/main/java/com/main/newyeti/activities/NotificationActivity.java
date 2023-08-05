package com.main.newyeti.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.adapter.NotificationAdapter;
import com.main.newyeti.model.Notification;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    NotificationAdapter notificationAdapter;
    RecyclerView notificationView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationView = findViewById(R.id.listNotification);
        progressBar = findViewById(R.id.progressBar);

        notificationView.addItemDecoration(
                new DividerItemDecoration(this, RecyclerView.VERTICAL));

        notificationAdapter = new NotificationAdapter(this);
        getListNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("MyLog", "NotificationActivity:onResume: ");
    }

    private void getListNotification() {
        loading(true);
        String id = DataLocalManager.getMyUserId();
        ApiService.apiService.getListFriendRequest(id).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(@NonNull Call<List<Notification>> call, @NonNull Response<List<Notification>> response) {
                loading(false);
                if (response.body() != null && response.isSuccessful()) {
                    Log.e("MyLog", "ListFriendsRequest:getListFriendRequest: onResponse: " + response.body());
                    List<Notification> friendListRequest = response.body();
                    if (friendListRequest.size() > 0) {
                        notificationAdapter.setListNotifications(friendListRequest);
                        notificationView.setAdapter(notificationAdapter);
//                        Toast.makeText(this, "Có " + list.size() + " bạn bè", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Log.e("MyLog", "ListFriendsRequest:getListFriendRequest: onResponse: " + response.code());
                    Toast.makeText(NotificationActivity.this, "Lỗi xác thực", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Log.e("MyLog", "ListFriendsRequest:getListFriendRequest: onFailure: response.body() null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Notification>> call, @NonNull Throwable t) {
                loading(false);
                Log.e("MyLog", "ListFriendsRequest:getListRequest: onFailure: " + t.getMessage());
                Toast.makeText(NotificationActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            notificationView.setVisibility(RecyclerView.INVISIBLE);
        } else {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            notificationView.setVisibility(RecyclerView.VISIBLE);
        }
    }
}