package com.main.newyeti.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.R;
import com.main.newyeti.utilities.DataLocalManager;

public class ProfileActivity extends AppCompatActivity {
    Button chat, friend;
    ImageView back;
    ImageView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        chat = findViewById(R.id.chat);
        friend = findViewById(R.id.addFriend);
        back = findViewById(R.id.back);
        edit = findViewById(R.id.edit);

        back.setOnClickListener(v -> onBackPressed());

        loadReceiverProfile();

        friend.setOnClickListener(v -> {
            if (friend.getVisibility() == View.VISIBLE) {
                switch (friend.getText().toString()) {
                    case "Kết bạn": {

                    }
                    case "Hủy kết bạn": {

                    }
                    case "Chấp nhận": {
                        acceptFriend();
                    }
                }
            }
        });

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

    // TODO:
    private void acceptFriend() {
    }
}