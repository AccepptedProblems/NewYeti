package com.main.newyeti.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.main.newyeti.R;
import com.main.newyeti.adapter.MessageAdapter;
import com.main.newyeti.model.Channel;
import com.main.newyeti.model.Message;
import com.main.newyeti.utilities.ApiService;
import com.main.newyeti.utilities.DataLocalManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {
    private Channel currentChannel;
    private TextView tvUsernameInChat;
    private ProgressBar progressBar;
    private RecyclerView rvMessage;
    private MessageAdapter listMessageAdapter;
    private ImageView btnSend;
    private EditText etMessage;
    private List<Message> listMessages = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        tvUsernameInChat = findViewById(R.id.tvUsernameInChat);
        etMessage = findViewById(R.id.etMessage);

        progressBar = findViewById(R.id.progressBar);
        rvMessage = findViewById(R.id.rvMessage);
        listMessageAdapter = new MessageAdapter(this);

        ImageView arrowBack = findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(v -> onBackPressed());

        CircleImageView avt = findViewById(R.id.avt);
        avt.setOnClickListener(v -> {
            Intent intent = new Intent(MessageActivity.this, ProfileActivity.class);
            intent.putExtra(DataLocalManager.KEY_PROFILE, DataLocalManager.VALUE_PROFILE_FRIEND);
            startActivity(intent);
        });

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> {
            sendMessage();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentChannel();
        tvUsernameInChat.setText(currentChannel.getReceiverName());
        getMessageInChannel();
    }

    private void getCurrentChannel() {
        // json to object
        String json = getIntent().getStringExtra(DataLocalManager.KEY_CURRENT_CHANNEL);
        Gson gson = new Gson();
        currentChannel = gson.fromJson(json, Channel.class);
        Log.e("MyLog", "MessageActivity getCurrentChannel: " + currentChannel.getId() + " " + currentChannel.getType());
    }

    private void getMessageInChannel() {
        if (listMessages == null)
            progressBar.setVisibility(ProgressBar.VISIBLE);

        ApiService.apiService.getListMessages(currentChannel.getId()).enqueue(new retrofit2.Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                progressBar.setVisibility(ProgressBar.GONE);
                if (response.isSuccessful()) {
                    listMessages = response.body();
                    if (listMessages != null) {
                        Log.e("MyLog", "getListMessage onResponse: " + listMessages.size());
                        listMessageAdapter.setMessageAdapter(listMessages, DataLocalManager.getMyUserId());
                        rvMessage.setAdapter(listMessageAdapter);
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(MessageActivity.this, "Phiên đăng nhập hết hạn", Toast.LENGTH_SHORT).show();
                    Log.e("MyLog", "getListMessage onResponse: response.code " + response.code());
                    Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("MyLog", "getListMessage onResponse: response.code " + response.code());
                    Toast.makeText(MessageActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
                progressBar.setVisibility(ProgressBar.GONE);
                Log.e("MyLog", "getListMessage onFailure: " + t.getMessage());
                Toast.makeText(MessageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        if (etMessage.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tin nhắn", Toast.LENGTH_SHORT).show();
            return;
        }
        Message message = null;
        LocalDateTime localDateTime;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.now();

            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = formatter.parse(formatter.format(date));
            } catch (ParseException e) {
                Log.e("MyLog", "sendMessage: " + e.getMessage());
            }
            Log.e("MyLog", "sendMessage: " + date.toString());
            message = new Message(currentChannel.getId(), DataLocalManager.getMyUserId(), Message.MessageType.TEXT, etMessage.getText().toString(), date);
        }

        ApiService.apiService.sendMessage(message).enqueue(new retrofit2.Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    Message message = response.body();
                    if (message != null) {
                        Log.e("MyLog", "sendMessage onResponse: " + message.getContent() + " " + message.getTimeSent());
                        etMessage.setText("");
                        getMessageInChannel();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(MessageActivity.this, "Phiên đăng nhập hết hạn", Toast.LENGTH_SHORT).show();
                    Log.e("MyLog", "sendMessage onResponse: response.code " + response.code());
                    Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("MyLog", "sendMessage onResponse: response.code " + response.code());
                    Toast.makeText(MessageActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Log.e("MyLog", "sendMessage onFailure: " + t.getMessage());
                Toast.makeText(MessageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}