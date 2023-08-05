package com.main.newyeti.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.activities.MessageActivity;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.UserViewHolder> {
    private final Context mContext;
    private List<User> listUser;

    public FriendAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
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
        User user = listUser.get(position);
        if (user == null) {
            return;
        }

        holder.resourceAvt.setImageResource(user.getResourceAvt());
        holder.nameUser.setText(user.getEmail());

        holder.itemFriend.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra(DataLocalManager.KEY_ID_RECEIVER_USER, user.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (listUser != null) {
            return listUser.size();
        }

        return 0;
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
