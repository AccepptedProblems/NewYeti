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

import androidx.fragment.app.Fragment;

import com.main.newyeti.R;
import com.main.newyeti.activities.LoginActivity;
import com.main.newyeti.activities.ProfileActivity;
import com.main.newyeti.utilities.DataLocalManager;

public class SettingFragment extends Fragment {
    private static Context context;
    private View view;


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

        Button info = view.findViewById(R.id.info);
        Button logout = view.findViewById(R.id.logout);
        TextView textNameSetting = view.findViewById(R.id.textNameSetting);
        TextView textUserNameSetting = view.findViewById(R.id.textUserNameSetting);

        textNameSetting.setText(DataLocalManager.getMyName());
        String email = DataLocalManager.getMyEmail();
        String username = "";
        if (email != null && email.contains("@")) {
            username = "@" + email.substring(0, email.indexOf("@"));
        }
        textUserNameSetting.setText(username);

        info.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), ProfileActivity.class);
            intent.putExtra(DataLocalManager.KEY_PROFILE, DataLocalManager.VALUE_PROFILE_MINE);
            startActivity(intent);
        });

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
}