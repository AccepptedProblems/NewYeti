package com.main.newyeti.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.R;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.DataLocalManager;

public class MessageActivity extends AppCompatActivity {
    private User receiverUser;
    private String nameReceiverUser;
    private TextView tvUsernameInChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        tvUsernameInChat = findViewById(R.id.tvUsernameInChat);
        ImageView arrowBack = findViewById(R.id.arrowBack);

        arrowBack.setOnClickListener(v -> onBackPressed());

        loadReceiverUser();
    }

    private void loadReceiverUser() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nameReceiverUser = bundle.getString(DataLocalManager.KEY_NAME_RECEIVER_USER);
            tvUsernameInChat.setText(nameReceiverUser);
        }
    }
}