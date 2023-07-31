package com.main.newyeti.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.main.newyeti.DataLocalManager;
import com.main.newyeti.R;
import com.main.newyeti.activity.LoginActivity;
import com.main.newyeti.api.ApiService;
import com.main.newyeti.model.Friend;
import com.main.newyeti.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragment extends Fragment {
    private View view;
    private Button info, logout;

    private TextView textNameSetting, textUserNameSetting;

    private static Context context;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        info = view.findViewById(R.id.info);
        logout = view.findViewById(R.id.logout);
        textNameSetting = view.findViewById(R.id.textNameSetting);
        textUserNameSetting = view.findViewById(R.id.textUserNameSetting);

        textNameSetting.setText(getUser().getDisplayName());
        textUserNameSetting.setText("@"+getUser().getUsername());

        logout.setOnClickListener(v -> openLogoutDialog());

        return view;
    }

    private void openLogoutDialog() {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_logout_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);

        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
        Button logoutBtn = dialog.findViewById(R.id.logoutBtn);

        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        logoutBtn.setOnClickListener(v -> {
            // Xóa Auth Token
            DataLocalManager.setApiKey("");
            DataLocalManager.setMyUserId("");
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
            // finish activity
            getActivity().finish();
        });

        dialog.show();
    }

    private static User getUser() {

        User user = new User();
        String id = DataLocalManager.getMyUserId();
        ApiService.apiService.getUserById(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null && response.isSuccessful()) {
                    user.setId(response.body().getId());
                    user.setDisplayName(response.body().getDisplayName());
                    user.setUsername(response.body().getUsername());
                    user.setEmail(response.body().getEmail());
                    user.setDayOfBirth(response.body().getDayOfBirth());
                    user.setGender(response.body().getGender());
                } else {
                    Toast.makeText(context, "Không tìm thấy thông tin", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return user;
    }
}