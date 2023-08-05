package com.main.newyeti.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.main.newyeti.fragment.ListChannelFragment;
import com.main.newyeti.fragment.ListFriendsFragment;
import com.main.newyeti.fragment.SettingFragment;

public class ViewpagerAdapter extends FragmentStateAdapter {
    public ViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ListFriendsFragment();
            case 2:
                return new SettingFragment();
            default:
                return new ListChannelFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
