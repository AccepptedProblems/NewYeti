package com.main.newyeti.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.activities.MainActivity;
import com.main.newyeti.activities.ProfileActivity;
import com.main.newyeti.model.Notification;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{

    private final Context mContext;
    private List<Notification> listNotifications;

    public NotificationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListNotifications(List<Notification> listNotifications) {
        this.listNotifications = listNotifications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = listNotifications.get(position);
        if (notification == null) {
            return;
        }

        holder.resourceAvt.setImageResource(R.drawable.avatar);
        holder.nameUser.setText(notification.getUser().getUsername());

        holder.btnAccept.setOnClickListener(v -> {
            acceptFriend(notification.getId());
        });

        holder.resourceAvt.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra(DataLocalManager.KEY_PROFILE, DataLocalManager.VALUE_PROFILE_ACCEPT_FRIEND);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (listNotifications != null) {
            return listNotifications.size();
        }

        return 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout itemNotification;
        private CircleImageView resourceAvt;
        private TextView nameUser;
        private Button btnAccept, btnDelete;

        public NotificationViewHolder(@NonNull View itemView) {

            super(itemView);

            itemNotification = itemView.findViewById(R.id.itemNotification);
            resourceAvt = itemView.findViewById(R.id.avtNotification);
            nameUser = itemView.findViewById(R.id.tvUsernameNotification);
            btnAccept = itemView.findViewById(R.id.accept);
            btnDelete = itemView.findViewById(R.id.delete);
        }
    }

    private void acceptFriend(String id) {

        ApiService.apiService.acceptFriend(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().equals("success")) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    Log.e("MyLog", "acceptFriend: onFailure: " + response.body());
                    Toast.makeText(mContext, "Các bạn đã trở thành bạn bè", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("MyLog", "acceptFriend: onFailure: " + response.body());
                    Toast.makeText(mContext, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("MyLog", "acceptFriend: onFailure: " + t.getMessage());
                Toast.makeText(mContext, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
