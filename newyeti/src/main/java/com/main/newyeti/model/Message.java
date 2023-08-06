package com.main.newyeti.model;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String id;
    private String channelId;
    private String userSendId;
    private MessageType type;
    private String content;
    private Date timeSent;

    public Message(String id, String channelId, String userSendId, MessageType type, String content, Date timeSent) {
        this.id = id;
        this.channelId = channelId;
        this.userSendId = userSendId;
        this.type = type;
        this.content = content;
        this.timeSent = timeSent;
    }

    public Message(String channelId, String userSendId, MessageType type, String content, Date timeSent) {
        this.channelId = channelId;
        this.userSendId = userSendId;
        this.type = type;
        this.content = content;
        this.timeSent = timeSent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUserSendId() {
        return userSendId;
    }

    public void setUserSendId(String userSendId) {
        this.userSendId = userSendId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeSent() {
        // format date to string like "hh:mm" if timeSent is today
        if (DateUtils.isToday(timeSent.getTime())) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            return formatter.format(timeSent);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            return formatter.format(timeSent);
        }
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public enum MessageType {
        TEXT
    }
}
