package com.main.newyeti.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.activities.MessageActivity;
import com.main.newyeti.model.Channel;
import com.main.newyeti.model.User;
import com.main.newyeti.utilities.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListChannelAdapter extends RecyclerView.Adapter<ListChannelAdapter.ChannelViewHolder> implements Filterable {
    private final Context mContext;
    private List<Channel> listChannel;
    private List<Channel> listChannelOld;

    public ListChannelAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListChannel(List<Channel> listChannel) {
        this.listChannel = listChannel;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Channel channel = listChannel.get(position);
        if (channel == null) {
            return;
        }
        User receiverUser = channel.getUsers()[1];
        holder.avtChat.setImageResource(receiverUser.getResourceAvt());
        holder.nameUser.setText(receiverUser.getDisplayName());
        if (channel.getLastMessage() != null) {
            holder.msgUser.setText(channel.getLastMessage().getContent());
        }

        holder.itemChat.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra(DataLocalManager.KEY_NAME_RECEIVER_USER, receiverUser.getDisplayName());
            intent.putExtra(DataLocalManager.KEY_ID_RECEIVER_USER, receiverUser.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (listChannel != null) {
            return listChannel.size();
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
                    listChannel = listChannelOld;
                } else {
                    List<Channel> list = new ArrayList<>();
                    for (Channel channel : listChannelOld) {
                        if (channel.getReceiverName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(channel);
                        }
                    }
                    listChannelOld = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listChannel;

                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listChannel = (List<Channel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        private final View itemChat;
        private final CircleImageView avtChat;
        private final TextView nameUser;
        private final TextView msgUser;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);

            itemChat = itemView.findViewById(R.id.itemChat);

            avtChat = itemView.findViewById(R.id.avtChat);
            nameUser = itemView.findViewById(R.id.tvUsername);
            msgUser = itemView.findViewById(R.id.tvMsgUser);
        }
    }
}
