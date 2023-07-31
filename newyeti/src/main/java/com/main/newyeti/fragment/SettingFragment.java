package com.main.newyeti.fragment;

import android.app.Dialog;
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

import androidx.fragment.app.Fragment;

import com.main.newyeti.R;
import com.main.newyeti.activities.LoginActivity;

public class SettingFragment extends Fragment {
    private View view;
    private Button info, logout;

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
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
            // finish activity
            getActivity().finish();
        });

        dialog.show();
    }
}