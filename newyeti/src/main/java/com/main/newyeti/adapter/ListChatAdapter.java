package com.main.newyeti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.model.Chat;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ChatViewHolder> {
    private Context mContext;
    private List<Chat> listChat;

    public ListChatAdapter(Context mContext, List<Chat> listChat) {
        this.mContext = mContext;
        this.listChat = listChat;
    }

    public void setListChat(List<Chat> listChat) {
        this.listChat = listChat;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = listChat.get(position);
        if (chat == null) {
            return;
        }
        holder.avtChat.setImageResource(chat.getResourceImg());
        holder.nameUser.setText(chat.getNameUser());
        holder.msgUser.setText(chat.getMsg());
    }

    @Override
    public int getItemCount() {
        if (listChat != null) {
            return listChat.size();
        }
        return 0;
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView avtChat;
        private TextView nameUser, msgUser;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            avtChat = itemView.findViewById(R.id.avtChat);
            nameUser = itemView.findViewById(R.id.tvUsername);
            msgUser = itemView.findViewById(R.id.tvMsgUser);
        }
    }
}