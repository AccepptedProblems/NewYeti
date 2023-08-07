package com.main.newyeti.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.R;
import com.main.newyeti.model.AddFriendReq;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    Button chat, friend;
    ImageView back;
    ImageView edit;
    String userId, relationshipId;
    TextView nameProfile, dateOfBirth, gender, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        chat = findViewById(R.id.chat);
        friend = findViewById(R.id.addFriend);
        back = findViewById(R.id.back);
        edit = findViewById(R.id.edit);
        nameProfile = findViewById(R.id.nameProfile);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        gender = findViewById(R.id.gender);
        email = findViewById(R.id.email);
        ImageView edit = findViewById(R.id.edit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = bundle.getString(DataLocalManager.KEY_USER_ID);
            relationshipId = bundle.getString(DataLocalManager.KEY_RELATIONSHIP_ID);
            getUserById(userId);
        }

        back.setOnClickListener(v -> onBackPressed());

        loadReceiverProfile();

        friend.setOnClickListener(v -> {
            if (friend.getVisibility() == View.VISIBLE) {
                switch (friend.getText().toString()) {
                    case "Kết bạn": {
                        addFriend(DataLocalManager.getMyUserId(), userId);
                        break;
                    }
                    case "Hủy kết bạn": {
                        cancelFriend();
                        break;
                    }
                    case "Chấp nhận": {
                        acceptFriend(relationshipId);
                    }
                }
            }
        });

        if(edit.getVisibility() == View.VISIBLE){
            edit.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            });
        }

        chat.setOnClickListener(v -> {
            if (chat.getVisibility() == View.VISIBLE) {
                // startChatActivity();
            }
        });

        edit.setOnClickListener(v -> {
            if (edit.getVisibility() == View.VISIBLE) {
                // editProfile();
            }
        });
    }

    private void loadReceiverProfile() {
        Bundle bundle = getIntent().getExtras();
        int nameReceiverProfile;
        if (bundle != null) {
            nameReceiverProfile = bundle.getInt(DataLocalManager.KEY_PROFILE);
            switch (nameReceiverProfile) {
                case DataLocalManager.VALUE_PROFILE_MINE: {
                    chat.setVisibility(View.GONE);
                    friend.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    break;
                }
                case DataLocalManager.VALUE_PROFILE_FRIEND: {
                    friend.setText("Hủy kết bạn");
                    friend.setCompoundDrawables(null, null, null, null);
                    break;
                }
                case DataLocalManager.VALUE_PROFILE_NO_FRIEND: {
                    chat.setVisibility(View.GONE);
                    friend.setText("Kết bạn");
                    break;
                }
                case DataLocalManager.VALUE_PROFILE_ACCEPT_FRIEND: {
                    chat.setVisibility(View.GONE);
                    friend.setText("Chấp nhận");
                    break;
                }

            }
        }
    }

    private void getUserById(String id) {
        ApiService.apiService.getUserById(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    Log.e("MyLog", "ProfileActivity: getUserById: onResponse: " + response.body());
                    if (response.body() != null) {
                        nameProfile.setText(response.body().getDisplayName());
                        dateOfBirth.setText(response.body().getDayOfBirthString());
                        gender.setText(response.body().getGender());
                        email.setText(response.body().getEmail());
                    } else {
                        Log.e("MyLog", "ProfileActivity: getUserById: onResponse: response.body() == null");
                        Toast.makeText(ProfileActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("MyLog", "ProfileActivity: getUserById: onResponse: " + response.code() + " " + response.message());
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("MyLog", "ProfileActivity: getUserById: onFailure: " + t.getMessage());
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // TODO:
    private void acceptFriend(String id) {
        ApiService.apiService.acceptFriend(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    ProfileActivity.this.startActivity(intent);
                    Log.e("MyLog", "ProfileActivity:acceptFriend: onFailure: " + response.body());
                    Toast.makeText(ProfileActivity.this, "Các bạn đã trở thành bạn bè", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    Log.e("MyLog", "ProfileActivity:acceptFriend: onFailure: " + response.body());
                    Toast.makeText(ProfileActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("MyLog", "ProfileActivity:acceptFriend: onFailure: " + response.body());
                    Toast.makeText(ProfileActivity.this, "Lỗi xác thực", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    ProfileActivity.this.startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("MyLog", "ProfileActivity:acceptFriend: onFailure: " + t.getMessage());
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addFriend(String idUserFrom, String idUserTo) {
        AddFriendReq addFriendReq = new AddFriendReq(idUserFrom, idUserTo);

        ApiService.apiService.addFriend(addFriendReq).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.e("MyLog", "ProfileActivity:addFriend onResponse: " + idUserFrom + " " + idUserTo + " " + DataLocalManager.getApiKey());
                if (response.body() != null && response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Add friend successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Add friend failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("MyLog", "UserAdapter:addFriend onFailure: " + t.getMessage());
                Toast.makeText(ProfileActivity.this, "Add friend failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelFriend() {
    }
}