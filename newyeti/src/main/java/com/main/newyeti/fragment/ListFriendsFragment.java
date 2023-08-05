package com.main.newyeti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.activities.LoginActivity;
import com.main.newyeti.activities.NotificationActivity;
import com.main.newyeti.activities.SearchFriendActivity;
import com.main.newyeti.adapter.FriendAdapter;
import com.main.newyeti.model.Friend;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFriendsFragment extends Fragment {
    FriendAdapter friendAdapter;
    private View view;
    private RecyclerView listFriendsView;
    private ProgressBar progressBar;

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

        ImageView notification = view.findViewById(R.id.notification);
        notification.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NotificationActivity.class);
            startActivity(intent);
        });

        Button searchList = view.findViewById(R.id.searchList);
        searchList.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
            startActivity(intent);
        });

        listFriendsView = view.findViewById(R.id.listFriendsView);
        friendAdapter = new FriendAdapter(getActivity());

        progressBar = view.findViewById(R.id.progressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        listFriendsView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getListFriend();
    }

    private void getListFriend() {
        progressBar.setVisibility(View.VISIBLE);

        List<User> list = new ArrayList<>();

        String header = "Bearer " + DataLocalManager.getApiKey();
        String id = DataLocalManager.getMyUserId();
        ApiService.apiService.getListFriends(id).enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(@NonNull Call<List<Friend>> call, @NonNull Response<List<Friend>> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {
                    Log.e("MyLog", "ListFriendsFragment:getListFriend: onResponse: " + response.body());
                    List<Friend> friendLists = response.body();
                    if (friendLists != null && friendLists.size() > 0) {
                        for (Friend friend : friendLists) {
                            User user = new User(friend.getUser());
                            user.setResourceAvt(R.drawable.avatar);
                            list.add(user);
                        }

                        friendAdapter.setListUser(list);
                        listFriendsView.setAdapter(friendAdapter);
                    } else {
                        Toast.makeText(getActivity(), "Bạn chưa có bạn bè nào", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("MyLog", "ListFriendsFragment:getListFriend: onResponse: " + response.code() + " " + response.message());
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Friend>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("MyLog", "ListFriendsFragment:getListFriend: onFailure: " + t.getMessage());
            }
        });
    }
}