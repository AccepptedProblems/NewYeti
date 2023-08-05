package com.main.newyeti.model;

import java.util.Date;

public class Message {
    private String id;
    private String channelId;
    private String senderId;

    private MessageType type;
    private String content;
    private Date timeSent;

    public Message(String id, String channelId, String senderId, MessageType type, String content, Date timeSent) {
        this.id = id;
        this.channelId = channelId;
        this.senderId = senderId;
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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public enum MessageType {
        TEXT
    }
}
