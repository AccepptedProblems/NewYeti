package com.main.newyeti.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.DataLocalManager;
import com.main.newyeti.R;
import com.main.newyeti.activity.LoginActivity;
import com.main.newyeti.activity.MainActivity;
import com.main.newyeti.activity.SearchFriendActivity;
import com.main.newyeti.adapter.UserAdapter;
import com.main.newyeti.api.ApiService;
import com.main.newyeti.model.Friend;
import com.main.newyeti.model.LoginResp;
import com.main.newyeti.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFriendsFragment extends Fragment {
    UserAdapter userAdapter;
    private View view;

    private Context context;
    private RecyclerView userView;

    public ListFriendsFragment() {
        // Required empty public constructor
    }

    public static ListFriendsFragment newInstance() {
        ListFriendsFragment fragment = new ListFriendsFragment();
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
        view = inflater.inflate(R.layout.fragment_list_friends, container, false);

        Button searchList = view.findViewById(R.id.searchList);
        searchList.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
            startActivity(intent);
        });

        userView = view.findViewById(R.id.listFriendsView);
        userAdapter = new UserAdapter(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        userView.setLayoutManager(linearLayoutManager);
        userAdapter.setListUser(getListUser());
        userView.setAdapter(userAdapter);

        return view;
    }

    private List<User> getListUser() {
        List<User> list = new ArrayList<>();

        String id = DataLocalManager.getMyUserId();
        ApiService.apiService.getListFriends(id).enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    List<Friend> friendLists = response.body();
                    for (Friend friend : friendLists) {
                        list.add(new User(R.drawable.avatar, friend.getUser().getUsername()));
                    }
                } else {
                    list.add(new User(R.drawable.avatar, "Lỗi"));
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }
}