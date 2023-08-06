package com.main.newyeti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.activities.LoginActivity;
import com.main.newyeti.adapter.ListChannelAdapter;
import com.main.newyeti.model.Channel;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListChannelFragment extends Fragment {
    private ListChannelAdapter listChannelAdapter;
    private View view;
    private RecyclerView rvListChannels;
    private ProgressBar progressBar;
    private SearchView searchView;

    public ListChannelFragment() {
        // Required empty public constructor
    }

    public static ListChannelFragment newInstance() {
        ListChannelFragment fragment = new ListChannelFragment();
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
        view = inflater.inflate(R.layout.fragment_list_channel, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        rvListChannels = view.findViewById(R.id.rvListChannels);
        rvListChannels.addItemDecoration(
                new DividerItemDecoration(view.getContext(), RecyclerView.VERTICAL));

        listChannelAdapter = new ListChannelAdapter(getActivity());

        searchView = view.findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listChannelAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listChannelAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MyLog", DataLocalManager.getMyUserId());
        getListChannels();
    }

    private void getListChannels() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService.apiService.getListChannels(DataLocalManager.getMyUserId()).enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(@NonNull Call<List<Channel>> call, @NonNull Response<List<Channel>> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {
                    List<Channel> listChannels = response.body();
                    if (listChannels != null && listChannels.size() > 0) {
                        Log.e("MyLog", "listChannel size: " + listChannels.size());
                        listChannelAdapter.setListChannel(listChannels);
                        rvListChannels.setAdapter(listChannelAdapter);
                    } else {
                        Toast.makeText(getActivity(), "Không có kênh nào", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("MyLog", "getListChannels: " + response.code() + " " + response.message());
                    Toast.makeText(getActivity(), "Phiên đăng nhập đã hết hạn", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Channel>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("MyLog", t.getMessage());
            }
        });
    }
}