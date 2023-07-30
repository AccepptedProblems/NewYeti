package com.main.newyeti.model;

public class Chat {
    private int resourceAvt;
    private String nameUser;
    private String msg;

    public Chat(int resourceAvt, String nameUser, String msg) {
        this.resourceAvt = resourceAvt;
        this.nameUser = nameUser;
        this.msg = msg;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResourceImg() {
        return resourceAvt;
    }
}
