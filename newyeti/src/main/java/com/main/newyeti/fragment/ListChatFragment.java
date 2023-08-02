package com.main.newyeti.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.activities.MainActivity;
import com.main.newyeti.adapter.ListChatAdapter;
import com.main.newyeti.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class ListChatFragment extends Fragment {
    private View view;
    private RecyclerView rvListChat;
    private MainActivity mainActivity;

    public ListChatFragment() {
        // Required empty public constructor
    }

    public static ListChatFragment newInstance() {
        ListChatFragment fragment = new ListChatFragment();
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
        view = inflater.inflate(R.layout.fragment_list_chat, container, false);
        mainActivity = (MainActivity) getActivity();

        List<Chat> listChat = new ArrayList<>();
        listChat.add(new Chat(R.drawable.avatar, "bóng", ":>>"));
        listChat.add(new Chat(R.drawable.avatar, "Xì trum", "Khò khò"));
        listChat.add(new Chat(R.drawable.avatar, "Siệu nhân gao", "Đã xem lúc 12:00"));
        listChat.add(new Chat(R.drawable.avatar, "Mark Zuckerberg", "Người này hiện không có mặt trên Messenger"));


        rvListChat = view.findViewById(R.id.rvListChat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvListChat.setLayoutManager(linearLayoutManager);
        rvListChat.addItemDecoration(
                new DividerItemDecoration(view.getContext(), RecyclerView.VERTICAL));
        rvListChat.setAdapter(new ListChatAdapter(getContext(), listChat));
        return view;
    }

}