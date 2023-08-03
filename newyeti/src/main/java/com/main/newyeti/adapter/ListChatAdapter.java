package com.main.newyeti.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.activities.MessageActivity;
import com.main.newyeti.model.Chat;
import com.main.newyeti.utilities.DataLocalManager;

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

        holder.itemChat.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra(DataLocalManager.KEY_NAME_RECEIVER_USER, chat.getNameUser());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (listChat != null) {
            return listChat.size();
        }
        return 0;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private final View itemChat;
        private final CircleImageView avtChat;
        private final TextView nameUser;
        private final TextView msgUser;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            itemChat = itemView.findViewById(R.id.itemChat);

            avtChat = itemView.findViewById(R.id.avtChat);
            nameUser = itemView.findViewById(R.id.tvUsername);
            msgUser = itemView.findViewById(R.id.tvMsgUser);
        }
    }
}
