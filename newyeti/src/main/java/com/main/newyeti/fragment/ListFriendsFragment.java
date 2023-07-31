package com.main.newyeti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.activities.SearchFriendActivity;
import com.main.newyeti.adapter.UserAdapter;
import com.main.newyeti.model.User;

import java.util.ArrayList;
import java.util.List;

public class ListFriendsFragment extends Fragment {
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
        userAdapter.setListUser(getListUser());
        userView.setAdapter(userAdapter);

        return view;
    }

    private List<User> getListUser() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User(R.drawable.avatar, "User name " + i));
        }
        return list;
    }
}