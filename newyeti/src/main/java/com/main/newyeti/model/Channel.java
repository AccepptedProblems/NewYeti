package com.main.newyeti.model;

public class Channel {
    private String id;
    private ChannelType type;
    private String name;
    private Message lastMessage;
    private User[] users;

    public Channel(String id, ChannelType type, String name, Message lastMessage, User[] users) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.lastMessage = lastMessage;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public enum ChannelType {
        DIRECT, GROUP, PRIVATE;
    }
}
