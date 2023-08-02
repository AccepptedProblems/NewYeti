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

import com.main.newyeti.R;
import com.main.newyeti.activities.SearchFriendActivity;
import com.main.newyeti.adapter.UserAdapter;
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
    private static Context context;
    UserAdapter userAdapter;
    private View view;
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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getListFriend();
    }

    private void getListFriend() {
        List<User> list = new ArrayList<>();
        Log.e("ListFriendsFragment", "getListFriend: " + DataLocalManager.getApiKey());


        String header = "Bearer " + DataLocalManager.getApiKey();
        String id = DataLocalManager.getMyUserId();
        ApiService.apiService.getListFriends(header, id).enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    List<Friend> friendLists = response.body();
                    for (Friend friend : friendLists) {
                        User user = new User(friend.getUser());
                        user.setResourceAvt(R.drawable.avatar);
                        list.add(user);
                    }
                } else {
                    list.add(new User(R.drawable.avatar, "Lỗi"));
                }
                if (list.size() > 0) {
                    userAdapter.setListUser(list);
                    userView.setAdapter(userAdapter);
                    Toast.makeText(context, "Có " + list.size() + " bạn bè", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}