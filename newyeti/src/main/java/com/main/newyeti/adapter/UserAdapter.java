package com.main.newyeti.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.model.AddFriendReq;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {
    private final Context mContext;
    private List<User> listUser;
    private List<User> listUserOld;

    public UserAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
        this.listUserOld = listUser;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
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
        holder.btnAddFriend.setOnClickListener(v -> {
            holder.loading(true);
            addFriend(DataLocalManager.getMyUserId(), user.getId());
            holder.btnAddFriend.setVisibility(View.GONE);
            holder.loading(false);
        });
    }

    @Override
    public int getItemCount() {
        if (listUser != null) {
            return listUser.size();
        }

        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    listUser = listUserOld;
                } else {
                    List<User> list = new ArrayList<>();
                    for (User user : listUserOld) {
                        if (user.getDisplayName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(user);
                        }
                    }

                    listUser = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listUser;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listUser = (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private void addFriend(String idUserFrom, String idUserTo) {
        AddFriendReq addFriendReq = new AddFriendReq(idUserFrom, idUserTo);

        ApiService.apiService.addFriend(addFriendReq).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.e("MyLog", "UserAdapter:addFriend onResponse: " + idUserFrom + " " + idUserTo + " " + DataLocalManager.getApiKey());
                if (response.body() != null && response.isSuccessful()) {
                    Toast.makeText(mContext, "Add friend successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Add friend failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("MyLog", "UserAdapter:addFriend onFailure: " + t.getMessage());
                Toast.makeText(mContext, "Add friend failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView resourceAvt;
        private final TextView nameUser;
        private final ImageButton btnAddFriend;
        private final ProgressBar progressBar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            resourceAvt = itemView.findViewById(R.id.avtUser);
            nameUser = itemView.findViewById(R.id.tvUsername);
            btnAddFriend = itemView.findViewById(R.id.ivAddFriend);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        void loading(boolean isLoading) {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }
}
