package com.main.newyeti.model;

public class AddFriendReq {
    String userFromId;
    String userToId;

    public AddFriendReq(String userFromId, String userToId) {
        this.userFromId = userFromId;
        this.userToId = userToId;
    }

    public String getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(String userFromId) {
        this.userFromId = userFromId;
    }

    public String getUserToId() {
        return userToId;
    }

    public void setUserToId(String userToId) {
        this.userToId = userToId;
    }
}
