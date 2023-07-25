package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        login = findViewById(R.id.textForwardLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}