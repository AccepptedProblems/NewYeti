package com.main.newyeti.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.activities.LoginActivity;
import com.main.newyeti.activities.MainActivity;
import com.main.newyeti.activities.ProfileActivity;
import com.main.newyeti.fragment.ListFriendsFragment;
import com.main.newyeti.model.Notification;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private final Context mContext;
    private List<Notification> listNotifications;

    public NotificationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListNotifications(List<Notification> listNotifications) {
        this.listNotifications = listNotifications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
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
            holder.loading(true);
            acceptFriend(notification.getId());
            holder.loading(false);
        });

        holder.btnDelete.setOnClickListener(v -> {
            holder.loading(true);
            cancelFriend(notification.getUser().getId());
            holder.loading(false);
        });

        holder.resourceAvt.setOnClickListener(v -> {
            goToProfile(DataLocalManager.VALUE_PROFILE_ACCEPT_FRIEND, notification.getUser().getId(), notification.getId());
        });
    }

    @Override
    public int getItemCount() {
        if (listNotifications != null) {
            return listNotifications.size();
        }

        return 0;
    }

    private void acceptFriend(String id) {
        ApiService.apiService.acceptFriend(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    Log.e("MyLog", "acceptFriend: onFailure: " + response.body());
                    Toast.makeText(mContext, "Các bạn đã trở thành bạn bè", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    Log.e("MyLog", "acceptFriend: onFailure: " + response.body());
                    Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("MyLog", "acceptFriend: onFailure: " + response.body());
                    Toast.makeText(mContext, "Lỗi xác thực", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("MyLog", "acceptFriend: onFailure: " + t.getMessage());
                Toast.makeText(mContext, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cancelFriend(String userId) {
        ApiService.apiService.deleteFriend(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.e("MyLog", "Notification:cancelFriend onResponse: " + response.body());
                if (response.body() != null && response.isSuccessful()) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "Cancel friend successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Cancel friend failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("MyLog", "Notification:cancelFriend onFailure: " + t.getMessage());
                Toast.makeText(mContext, "Cancel friend failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToProfile(int typeProfile, String userid, String relationshipId) {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        intent.putExtra(DataLocalManager.KEY_PROFILE, typeProfile);
        intent.putExtra(DataLocalManager.KEY_USER_ID, userid);
        intent.putExtra(DataLocalManager.KEY_RELATIONSHIP_ID, relationshipId);
        mContext.startActivity(intent);
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView resourceAvt;
        private final TextView nameUser;
        private final Button btnAccept;
        private final Button btnDelete;
        private final ProgressBar progressBar;
        private LinearLayout itemNotification;

        public NotificationViewHolder(@NonNull View itemView) {

            super(itemView);

            itemNotification = itemView.findViewById(R.id.itemNotification);
            resourceAvt = itemView.findViewById(R.id.avtNotification);
            nameUser = itemView.findViewById(R.id.tvUsernameNotification);
            btnAccept = itemView.findViewById(R.id.accept);
            btnDelete = itemView.findViewById(R.id.delete);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public void loading(boolean isLoading) {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
                btnAccept.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                btnAccept.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
            }
        }
    }
}
