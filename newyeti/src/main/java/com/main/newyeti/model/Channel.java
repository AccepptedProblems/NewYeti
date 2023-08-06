package com.main.newyeti.model;

import com.main.newyeti.R;
import com.main.newyeti.utilities.DataLocalManager;

public class Channel {
    private String id;
    private ChannelType type;
    private String name;
    private Message latestMess;
    private User[] users;

    public Channel(String id, ChannelType type, String name, Message latestMess, User[] users) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.latestMess = latestMess;
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

    public Message getLatestMess() {
        return latestMess;
    }

    public void setLatestMess(Message latestMess) {
        this.latestMess = latestMess;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public String getNameChannel() {
        if (type == ChannelType.DIRECT) {
            for (User user : users) {
                if (!user.getId().equals(DataLocalManager.getMyUserId()))
                    return user.getDisplayName();
            }
        } else {
            return name;
        }
        return "PRIVATE";
    }

    public int getResourceAvt() {
        if (type == ChannelType.DIRECT) {
            for (User user : users) {
                if (!user.getId().equals(DataLocalManager.getMyUserId()))
//                    return user.getResourceAvt();
                    return R.drawable.avatar;
            }
        } else {
            return R.drawable.avatar;
        }
        return R.drawable.avatar;
    }

    public String getReceiverName() {
        if (type == ChannelType.DIRECT) {
            for (User user : users) {
                if (!user.getId().equals(DataLocalManager.getMyUserId()))
                    return user.getDisplayName();
            }
        } else {
            return "Group";
        }
        return "PRIVATE";
    }

    public User getReceiveUser() {
        if (type == ChannelType.DIRECT) {
            for (User user : users) {
                if (!user.getId().equals(DataLocalManager.getMyUserId()))
                    return user;
            }
        } else {
            return null;
        }
        return null;
    }

    public enum ChannelType {
        DIRECT, GROUP, PRIVATE;
    }
}
