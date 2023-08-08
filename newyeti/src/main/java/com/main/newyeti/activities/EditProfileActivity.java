package com.main.newyeti.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

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

public class EditProfileActivity extends AppCompatActivity {

    private Button btnEditDate, btnUpdate;
    private DatePickerDialog datePickerDialog;
    private ProgressBar progressBar;
    private EditText editName, etPasswordRegis, rePasswordRegis;
    private RadioButton radioMale, radioFemale;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnEditDate = findViewById(R.id.btnEditDate);
        btnUpdate = findViewById(R.id.btnUpdate);
        editName = findViewById(R.id.editName);
        etPasswordRegis = findViewById(R.id.etPasswordRegis);
        rePasswordRegis = findViewById(R.id.rePasswordRegis);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        progressBar = findViewById(R.id.progressBar);

        btnEditDate.setText(DataLocalManager.getBirthday());
        editName.setText(DataLocalManager.getMyName());
        etPasswordRegis.setText(DataLocalManager.getMyPassword());
        if (DataLocalManager.getMyGender() == "MALE") {
            radioMale.isChecked();
        } else {
            radioFemale.isChecked();
        }

        initDatePicker();
        btnEditDate.setOnClickListener(v -> datePickerDialog.show());

        btnUpdate.setOnClickListener(v -> {
                updateUser();
        });


    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = day + "-" + month + "-" + year;
            btnEditDate.setText(date);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void updateUser() {
        loading(true);
        User user = new User();

        String dateOfBirthString = btnEditDate.getText().toString();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat.parse(dateOfBirthString); // Chuyển đổi chuỗi thành kiểu Date
            String convertedDate = outputFormat.format(date); // Chuyển đổi kiểu Date thành chuỗi định dạng mới
            Date dateOfBirth = outputFormat.parse(convertedDate); // Chuyển đổi chuỗi thành kiểu Date
            user.setDayOfBirth(dateOfBirth);
        } catch (ParseException e) {
            Log.e("MyLog", "Register checkInput: " + e.getMessage());
            e.printStackTrace();
        }

        if (rePasswordRegis == null || rePasswordRegis.getText().toString().isEmpty()) {
            user.setPassword(etPasswordRegis.getText().toString());
        } else {
            user.setPassword(rePasswordRegis.getText().toString());
        }

        if (radioMale.isChecked()) {
            user.setGender("MALE");
        } else {
            user.setGender("FEMALE");
        }

        user.setDisplayName(editName.getText().toString());

        String email = DataLocalManager.getMyEmail();
        user.setEmail(email);
        user.setUsername(email.substring(0, email.indexOf("@")));
        ApiService.apiService.updateUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                loading(false);
                Toast.makeText(EditProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                DataLocalManager.setMyName(user.getDisplayName());
                DataLocalManager.setMyBirthday(user.getDayOfBirthString());
                DataLocalManager.setMyGender(user.getGender());
                DataLocalManager.setMyPassword(user.getPassword());
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                loading(false);
                Log.e("MyLog", "EditProfileActivity: updateUser: " + t.getLocalizedMessage());
                Toast.makeText(EditProfileActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private User getNewInfo() {
//        User user = new User();
//        String dateOfBirthString = btnEditDate.getText().toString().trim();
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date dateOfBirth;
//        try {
//            dateOfBirth = format.parse(dateOfBirthString);
//            user.setDayOfBirth(dateOfBirth);
//        } catch (ParseException e) {
//            Log.e("MyLog", "Register checkInput: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//        user.setDisplayName(editName.getText().toString());
//        if (rePasswordRegis == null || rePasswordRegis.getText().toString().isEmpty()) {
//            user.setPassword(etPasswordRegis.getText().toString());
//        } else {
//            user.setPassword(rePasswordRegis.getText().toString());
//        }
//        if (radioMale.isChecked()) {
//            user.setGender("MALE");
//        } else {
//            user.setGender("FEMALE");
//        }
//
//        return user;
//    }

    private void loading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
        }
    }
}