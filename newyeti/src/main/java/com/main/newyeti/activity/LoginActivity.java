package com.main.newyeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.DataLocalManager;
import com.main.newyeti.R;
import com.main.newyeti.api.ApiService;
import com.main.newyeti.model.LoginResp;
import com.main.newyeti.model.User;

import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private TextView signup;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.tvRegis);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);

        login.setOnClickListener(view -> login());

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void login() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        Log.e("Login", "login: " + emailText + " " + passwordText);

        ApiService.apiService.login(new User(emailText, passwordText)).enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    // Lấy apiKey, userId để lưu vào shared preferences
                    String apiKey = response.body().getApiKey();
                    String userId = response.body().getUser().getId();
                    // Lưu vào shared preferences
                    DataLocalManager.setApiKey(apiKey);
                    DataLocalManager.setMyUserId(userId);
                    //Tạo Intent để truyền dữ liệu sang Main
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                Log.e("Login", "onFailure: " + t.getMessage());
            }
        });
    }
}