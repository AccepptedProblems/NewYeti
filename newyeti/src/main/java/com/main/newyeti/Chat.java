package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Chat extends AppCompatActivity {

    private int resourceImg;
    private String nameUser;
    private String msg;

    public Chat(int resourceImg, String nameUser, String msg) {
        this.resourceImg = resourceImg;
        this.nameUser = nameUser;
        this.msg = msg;
    }

    public int getResourceImg() {
        return resourceImg;
    }

    public void setResourceImg(int resourceImg) {
        this.resourceImg = resourceImg;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }




}