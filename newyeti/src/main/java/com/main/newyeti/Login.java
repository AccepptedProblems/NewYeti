package com.main.newyeti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.api.ApiService;
import com.main.newyeti.model.Token;
import com.main.newyeti.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private Button login;
    private TextView signup;
    private EditText email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.textForwardRegis);

        email = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);

        login.setOnClickListener(view -> {
            login();


        });

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Registration.class);
            startActivity(intent);
            finish();
        });

    }

    private void login() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        Log.e("Login", "login: " + emailText + " " + passwordText);

        ApiService.apiService.login(new User(emailText, passwordText)).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Toast.makeText(Login.this, response.body().getApiKey(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(Login.this, "Login Failed s", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                Log.e("Login", "onFailure: " + t.getMessage());
            }
        });
    }
}