package com.main.newyeti.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.newyeti.model.Token;
import com.main.newyeti.model.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    String BASE_URL = "http://localhost:8081/";

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);

    @POST("v1/api/user/login")
    Call<Token> login(@Body User user);

    @POST("v1/api/user/register")
    Call<User> register(@Body User user);

}
