package com.main.newyeti.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.R;
import com.main.newyeti.utilities.DataLocalManager;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loadReceiverProfile();

    }

    private void loadReceiverProfile() {

        Button chat = findViewById(R.id.chat);
        Button friend = findViewById(R.id.addFriend);

        Bundle bundle = getIntent().getExtras();
        int nameReceiverProfile;
        if (bundle != null) {
            nameReceiverProfile = bundle.getInt(DataLocalManager.KEY_PROFILE);
            switch (nameReceiverProfile) {
                case DataLocalManager.VALUE_PROFILE_MINE: {
                    chat.setVisibility(View.GONE);
                    friend.setVisibility(View.GONE);
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
}