package com.main.newyeti.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.R;
import com.main.newyeti.api.ApiService;
import com.main.newyeti.model.Token;
import com.main.newyeti.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText email, password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        login = findViewById(R.id.btnLogin);
        TextView signup = findViewById(R.id.tvRegis);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);

        progressBar = findViewById(R.id.progressBar);

        login.setOnClickListener(view -> {
            User user = checkValid();
            if (user != null) {
                login(user);
            }
        });

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void login(User user) {
        loading(true);
        ApiService.apiService.login(user).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                loading(false);
                if (response.body() != null && response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                loading(false);
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                Log.e("Login", "onFailure: " + t.getMessage());
            }
        });
    }

    private void loading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
        }
    }

    private User checkValid() {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if (emailText.isEmpty()) {
            email.setError("Email không được để trống");
            return null;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Email không hợp lệ");
            return null;
        } else if (passwordText.isEmpty()) {
            password.setError("Mật khẩu không được để trống");
            return null;
        } else {
            return new User(emailText, passwordText);
        }
    }
}