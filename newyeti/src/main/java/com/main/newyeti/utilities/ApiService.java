package com.main.newyeti.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.newyeti.model.AddFriendReq;
import com.main.newyeti.model.Channel;
import com.main.newyeti.model.Friend;
import com.main.newyeti.model.LoginReq;
import com.main.newyeti.model.LoginResp;
import com.main.newyeti.model.Message;
import com.main.newyeti.model.Notification;
import com.main.newyeti.model.User;
import com.main.newyeti.model.UserRegister;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // TODO: Change this to your IP address, ipconfig in cmd -> adapter wifi -> IPv4 Address
    String BASE_URL = "http://10.90.21.4:8081/";
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
            .client(okBuilder.build())
            .build().create(ApiService.class);

    @POST("v1/api/user/login")
    Call<LoginResp> login(@Body LoginReq user);

    @POST("v1/api/user/register")
    Call<User> register(@Body UserRegister user);

    @GET("v1/api/user/{id}")
    Call<User> getUserById(@Path("id") String id);

    @GET("v1/api/friend/{id}/list")
    Call<List<Friend>> getListFriends(@Path("id") String id);

    @GET("v1/api/user/all")
    Call<List<User>> getListUsers();
    // @Header("Authorization") String apikey

    @POST("v1/api/friend")
    Call<User> addFriend(@Body AddFriendReq addFriendReq);

    @GET("v1/api/channel")
    Call<List<Channel>> getListChannels(@Query("userId") String id);

    @GET("v1/api/friend/{id}/request")
    Call<List<Notification>> getListFriendRequest(@Path("id") String id);

    @PUT("v1/api/friend/{id}/confirm")
    Call<User> acceptFriend(@Path("id") String id);

    @GET("v1/api/message")
    Call<List<Message>> getListMessages(@Query("channelId") String channelId);

    @GET("v1/api/channel/direct")
    Call<Channel> getChannelById(@Query("userId") String userId);

    @POST("v1/api/message")
    Call<Message> sendMessage(@Body Message message);

    @PUT("v1/api/user")
    Call<User> updateUser(@Body User user);

    @DELETE("v1/api/friend/{id}")
    Call<User> deleteFriend(@Path("id") String id);

    @DELETE("v1/api/message/{id}")
    Call<Message> deleteMessage(@Path("id") String id);

}
