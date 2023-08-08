package com.main.newyeti.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.main.newyeti.R;
import com.main.newyeti.model.Message;
import com.main.newyeti.utilities.ApiService;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int VIEW_TYPE_SENT_MESSAGE = 1;
    static final int VIEW_TYPE_RECEIVED_MESSAGE = 2;
    private final Context mContext;
    private List<Message> listMessage;
    private String idSender;

    public MessageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private void deleteMessage(String messageId) {
        ApiService.apiService.deleteMessage(messageId).enqueue(new retrofit2.Callback<Message>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Message> call, @NonNull retrofit2.Response<Message> response) {
                if (response.isSuccessful()) {
                    listMessage.removeIf(message -> message.getId().equals(messageId));
                    Log.e("MyLog", "deleteMessage: " + messageId);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<Message> call, @NonNull Throwable t) {
                Log.e("MyLog", "onFailure: " + t.getMessage());
                Toast.makeText(mContext, "Delete message failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMessageAdapter(List<Message> listMessage, String idSender) {
        this.listMessage = listMessage;
        this.idSender = idSender;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT_MESSAGE) {
            return new SentMessageViewHolder(
                    LayoutInflater.from(parent.getContext()).
                            inflate(R.layout.item_sent_message, parent, false));
        }
        return new ReceivedMessageViewHolder(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_received_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT_MESSAGE) {
            ((SentMessageViewHolder) holder).setData(listMessage.get(position));

            ((SentMessageViewHolder) holder).tvDelete.setOnClickListener(v -> {
                deleteMessage(listMessage.get(position).getId());
            });
        } else {
            ((ReceivedMessageViewHolder) holder).setData(listMessage.get(position));

            ((ReceivedMessageViewHolder) holder).tvDelete.setOnClickListener(v -> {
                deleteMessage(listMessage.get(position).getId());
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    public int getItemViewType(int position) {
        Message message = listMessage.get(position);
        if (message == null) {
            return -1;
        }

        if (message.getUserSendId().equals(idSender)) {
            return VIEW_TYPE_SENT_MESSAGE;
        }
        return VIEW_TYPE_RECEIVED_MESSAGE;
    }

    public static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout itemSentMessage;
        private final TextView tvMessage;
        private final TextView tvTime;
        private final TextView tvDelete;
        private String messageId;

        public SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            itemSentMessage = itemView.findViewById(R.id.item_sent_message);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDelete = itemView.findViewById(R.id.tvDelete);

            tvMessage.setMaxWidth((int) (itemView.getResources().getDisplayMetrics().widthPixels * 0.7));

            tvMessage.setOnClickListener(v
                    -> tvTime.setVisibility(tvTime.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));

            tvMessage.setOnLongClickListener(v -> {
                tvDelete.setVisibility(View.VISIBLE);
                itemSentMessage.setOnClickListener(v1 -> tvDelete.setVisibility(View.GONE));
                return true;
            });
        }

        void setData(Message message) {
            tvMessage.setText(message.getContent());
            tvTime.setText(message.getTimeSent());
            messageId = message.getId();
        }
    }

    public static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout itemReceivedMessage;
        private final TextView tvMessage;
        private final TextView tvTime;
        private final TextView tvDelete;


        public ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            itemReceivedMessage = itemView.findViewById(R.id.item_received_message);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDelete = itemView.findViewById(R.id.tvDelete);

            tvMessage.setMaxWidth((int) (itemView.getResources().getDisplayMetrics().widthPixels * 0.7));

            tvMessage.setOnClickListener(v
                    -> tvTime.setVisibility(tvTime.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));

            tvMessage.setOnLongClickListener(v -> {
                tvDelete.setVisibility(View.VISIBLE);
                itemReceivedMessage.getRootView().setOnClickListener(v1 -> tvDelete.setVisibility(View.GONE));
                return true;
            });
        }

        void setData(Message message) {
            tvMessage.setText(message.getContent());
            tvTime.setText(message.getTimeSent());
        }
    }
}
