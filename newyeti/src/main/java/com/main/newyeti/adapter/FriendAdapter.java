package com.main.newyeti.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.main.newyeti.R;
import com.main.newyeti.activities.MessageActivity;
import com.main.newyeti.model.Channel;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.UserViewHolder> {
    private final Context mContext;
    private List<User> listFriend;

    public FriendAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListFriend(List<User> listFriend) {
        this.listFriend = listFriend;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User friend = listFriend.get(position);

        if (friend == null) {
            return;
        }
        String userID = friend.getId();
        Log.e("MyLog", "Friend Adapter onBindViewHolder: id: " + userID);

        holder.resourceAvt.setImageResource(friend.getResourceAvt());
        holder.nameUser.setText(friend.getEmail());

        holder.itemFriend.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MessageActivity.class);
            Channel channel = getChannel(userID);
            Gson gson = new Gson();
            String json = gson.toJson(channel);
            intent.putExtra(DataLocalManager.KEY_CURRENT_CHANNEL, json);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (listFriend != null) {
            return listFriend.size();
        }

        return 0;
    }

    private Channel getChannel(String id) {
        Channel[] channel = {null};
        Log.e("MyLog", "FriendAdapter-getChannel: " + id);
        ApiService.apiService.getChannelById(id).enqueue(new retrofit2.Callback<Channel>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Channel> call, @NonNull Response<Channel> response) {
                if (response.isSuccessful()) {
                    channel[0] = response.body();
                    Log.e("MyLog", "FriendAdapter-getChannel: " + response);
                } else {
                    Log.e("MyLog", "FriendAdapter-getChannel: " + response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<Channel> call, @NonNull Throwable t) {
                Log.e("MyLog", "getChannel: " + t.getMessage());
            }
        });
        return channel[0];
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout itemFriend;
        private final CircleImageView resourceAvt;
        private final TextView nameUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            resourceAvt = itemView.findViewById(R.id.avtUser);
            nameUser = itemView.findViewById(R.id.tvUsername);
            itemFriend = itemView.findViewById(R.id.itemFriend);
        }
    }
}
