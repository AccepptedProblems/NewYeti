package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Setting extends AppCompatActivity {

    private Button listChats, listFriends, setting, info, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        listChats = findViewById(R.id.listChats);
        listFriends = findViewById(R.id.listFriends);
        setting = findViewById(R.id.setting);
        info = findViewById(R.id.info);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoutDialog();
            }
        });

        listFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, ListFriends.class);
                startActivity(intent);
                finish();
            }
        });

        listChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void openLogoutDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_logout_dialog);

        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);

        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
        Button logoutBtn = dialog.findViewById(R.id.logoutBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }
}