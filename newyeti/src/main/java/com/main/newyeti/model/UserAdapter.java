package com.main.newyeti.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.Chat;
import com.main.newyeti.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext;
    private List<User> listUser;

    public UserAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = listUser.get(position);
        if (user == null) {
            return;
        }

        holder.resourceAvt.setImageResource(user.getResourceAvt());
        holder.nameUser.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        if(listUser != null) {
            return listUser.size();
        }

        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView resourceAvt;
        private TextView nameUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            resourceAvt = itemView.findViewById(R.id.avtUser);
            nameUser = itemView.findViewById(R.id.nameUser);
        }

    }
}
