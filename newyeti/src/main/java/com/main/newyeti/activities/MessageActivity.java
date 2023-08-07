package com.main.newyeti.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.R;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.DataLocalManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    private User receiverUser;
    private String nameReceiverUser;
    private String idReceiverUser;
    private TextView tvUsernameInChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        tvUsernameInChat = findViewById(R.id.tvUsernameInChat);
        ImageView arrowBack = findViewById(R.id.arrowBack);

        arrowBack.setOnClickListener(v -> onBackPressed());
        CircleImageView avt = findViewById(R.id.avt);
        avt.setOnClickListener(v -> {
            goToProfile(DataLocalManager.VALUE_PROFILE_FRIEND, idReceiverUser);
        });

        loadReceiverUser();
    }

    private void loadReceiverUser() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nameReceiverUser = bundle.getString(DataLocalManager.KEY_NAME_RECEIVER_USER);
            idReceiverUser = bundle.getString(DataLocalManager.KEY_ID_RECEIVER_USER);
            tvUsernameInChat.setText(nameReceiverUser);
        }
    }

    private void goToProfile(int typeProfile, String idUser) {
        Intent intent = new Intent(MessageActivity.this, ProfileActivity.class);
        intent.putExtra(DataLocalManager.KEY_PROFILE, typeProfile);
        intent.putExtra(DataLocalManager.KEY_USER_ID, idUser);
        startActivity(intent);
    }
}