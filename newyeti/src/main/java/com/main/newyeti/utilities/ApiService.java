package com.main.newyeti.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.newyeti.model.AddFriendReq;
import com.main.newyeti.model.Friend;
import com.main.newyeti.model.LoginResp;
import com.main.newyeti.model.User;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    // TODO: Change this to your IP address, ipconfig in cmd -> adapter wifi -> IPv4 Address
    String BASE_URL = "http://192.168.1.109:8081/";
    String header = "Bearer " + DataLocalManager.getApiKey();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    Interceptor interceptor = chain -> {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Authorization", header);
        return chain.proceed(builder.build());
    };

    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder().addInterceptor(interceptor);

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okBuilder.build())
            .build().create(ApiService.class);


    @POST("v1/api/user/login")
    Call<LoginResp> login(@Body User user);

    @POST("v1/api/user/register")
    Call<User> register(@Body User user);

    @GET("v1/api/user/{id}")
    Call<User> getUserById(@Path("id") String id);

    @GET("v1/api/friend/{id}/list")
    Call<List<Friend>> getListFriends(@Header("Authorization") String apikey, @Path("id") String id);

    @GET("v1/api/user/all")
    Call<List<User>> getListUsers(@Header("Authorization") String apikey);

    @POST("v1/api/friend")
    Call<User> addFriend(@Header("Authorization") String apikey, @Body AddFriendReq addFriendReq);
}
