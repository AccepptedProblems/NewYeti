package com.main.newyeti.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.main.newyeti.R;
import com.main.newyeti.adapter.ViewpagerAdapter;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        viewPager2 = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNav);

        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(this);
        viewPager2.setAdapter(viewpagerAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_list_message).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_list_friends).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_settings).setChecked(true);
                        break;
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_list_message) {
                viewPager2.setCurrentItem(0);
            } else if (item.getItemId() == R.id.menu_list_friends) {
                viewPager2.setCurrentItem(1);
            } else if (item.getItemId() == R.id.menu_settings) {
                viewPager2.setCurrentItem(2);
            }
            return true;
        });
    }
}