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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.R;
import com.main.newyeti.model.LoginResp;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

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

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("Login", "onResume" + DataLocalManager.getMyEmail() + " " + DataLocalManager.getMyPassword());

        if (DataLocalManager.getMyEmail() != null && DataLocalManager.getMyPassword() != null
                && !DataLocalManager.getMyEmail().equals("") && !DataLocalManager.getMyPassword().equals("")) {
            email.setText(DataLocalManager.getMyEmail());
            password.setText(DataLocalManager.getMyPassword());
        }
    }

    private void login(User user) {
        loading(true);
        ApiService.apiService.login(user).enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(@NonNull Call<LoginResp> call, @NonNull Response<LoginResp> response) {
                loading(false);
                if (response.body() != null && response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Log.e("Login", "Success" + response.body().getApiKey());

                    // Lấy apiKey, userId để lưu vào shared preferences
                    String apiKey = response.body().getApiKey();
                    User res = response.body().getUser();

                    // Lưu vào shared preferences
                    DataLocalManager.setApiKey(apiKey);
                    DataLocalManager.setMyUserId(res.getId());
                    DataLocalManager.setMyName(res.getDisplayName());
                    DataLocalManager.setMyEmail(res.getEmail());
                    DataLocalManager.setMyPassword(res.getPassword());

                    //Tạo Intent để truyền dữ liệu sang Main
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Log.e("Login", "Failed" + response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResp> call, @NonNull Throwable t) {
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