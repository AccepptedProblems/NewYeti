package com.main.newyeti.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.R;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button regis;
    ProgressBar progressBar;
    private EditText nameRegis, emailRegis, passwordRegis, rePasswordRegis;
    private RadioButton radioMale;
    private Button btnChooseDate;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        TextView login = findViewById(R.id.tvLogin);
        regis = findViewById(R.id.btnRegis);
        progressBar = findViewById(R.id.progressBar);

        btnChooseDate = findViewById(R.id.btnChooseDate);
        nameRegis = findViewById(R.id.etNameRegis);
        emailRegis = findViewById(R.id.etEmailRegis);
        passwordRegis = findViewById(R.id.etPasswordRegis);
        rePasswordRegis = findViewById(R.id.rePasswordRegis);
        radioMale = findViewById(R.id.radioMale);

        login.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        regis.setOnClickListener(v -> {
            User user = checkInput();

            if (user != null)
                register(user);
        });

        initDatePicker();
        btnChooseDate.setOnClickListener(v -> datePickerDialog.show());
    }

    private void register(User user) {
        loading(true);
        ApiService.apiService.register(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null && response.isSuccessful()) {
                    loading(false);
                    showToast("Đăng ký thành công");

                    DataLocalManager.setMyEmail(response.body().getEmail());
                    DataLocalManager.setMyPassword(response.body().getPassword());

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    loading(false);
                    showToast("Đăng ký thất bại");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast("Đăng ký thất bại");
                Log.e("Login", "onFailure: " + t.getMessage());
            }
        });
    }

    private void loading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            regis.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            regis.setVisibility(View.VISIBLE);
        }

    }

    private User checkInput() {
        String email = emailRegis.getText().toString().trim();
        String password = passwordRegis.getText().toString().trim();
        String rePassword = rePasswordRegis.getText().toString().trim();
        String name = nameRegis.getText().toString().trim();

        if (name.isEmpty()) {
            nameRegis.setError("Tên không được để trống");
            return null;
        } else if (email.isEmpty()) {
            emailRegis.setError("Email không được để trống");
            return null;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailRegis.setError("Email không hợp lệ");
            return null;
        } else if (password.isEmpty()) {
            passwordRegis.setError("Mật khẩu không được để trống");
            return null;
        } else if (rePassword.isEmpty()) {
            rePasswordRegis.setError("Mật khẩu không được để trống");
            return null;
        } else if (!password.equals(rePassword)) {
            rePasswordRegis.setError("Mật khẩu không khớp");
            return null;
        } else {
            String username = email.substring(0, email.indexOf("@"));
            String gender = radioMale.isChecked() ? "MALE" : "FEMALE";

            String dateOfBirthString = btnChooseDate.getText().toString().trim();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date dateOfBirth;

            try {
                dateOfBirth = format.parse(dateOfBirthString);
            } catch (ParseException e) {
                Log.e("Register", "checkInput: " + e.getMessage());
                throw new RuntimeException(e);
            }
            return new User(username, password, email, name, gender, dateOfBirth);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            btnChooseDate.setText(date);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
}