package com.main.newyeti;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.main.newyeti.api.ApiService;
import com.main.newyeti.model.Token;
import com.main.newyeti.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    private TextView login;
    private EditText nameRegis, emailRegis, passwordRegis, rePasswordRegis;
    private RadioButton radioMale, radioFemale;
    private Button btnChooseDate, regis;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        login = findViewById(R.id.textForwardLogin);
        regis = findViewById(R.id.btnRegis);
        btnChooseDate = findViewById(R.id.btnChooseDate);
        nameRegis = findViewById(R.id.etNameRegis);
        emailRegis = findViewById(R.id.etEmailRegis);
        passwordRegis = findViewById(R.id.etPasswordRegis);
        rePasswordRegis = findViewById(R.id.rePasswordRegis);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);


        login.setOnClickListener(view -> {
            Intent intent = new Intent(Registration.this, Login.class);
            startActivity(intent);
            finish();
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailRegis.getText().toString();
                String password = passwordRegis.getText().toString();
                String name = nameRegis.getText().toString();
                String gender = "";
                if (radioMale.isChecked()) {
                    gender = "MALE";
                } else gender = "FEMALE";
            }
        });

        initDatePicker();
        btnChooseDate.setOnClickListener(v -> openDatePicker());
    }

    private void register(User user) {

        ApiService.apiService.login(user).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Toast.makeText(Registration.this, response.body().getApiKey(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(Registration.this, "Login Failed s", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(Registration.this, "Login Failed", Toast.LENGTH_SHORT).show();
                Log.e("Login", "onFailure: " + t.getMessage());
            }
        });
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

    private void openDatePicker() {
        datePickerDialog.show();
    }
}