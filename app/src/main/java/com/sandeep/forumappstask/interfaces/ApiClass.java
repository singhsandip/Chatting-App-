package com.sandeep.forumappstask.interfaces;

import com.sandeep.forumappstask.model.ChatResponse;
import com.sandeep.forumappstask.model.GetRegisteredUsers;
import com.sandeep.forumappstask.model.LoginResponse;
import com.sandeep.forumappstask.model.RegisterResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by sandeep on 16-06-2017.
 */

public interface ApiClass
{
    @FormUrlEncoded
    @POST("/register.php")
    public void registerUser(@Field("email") String email,
                             @Field("token") String token,
                             @Field("password") String password,
                             Callback<RegisterResponse> responseCallback);

    @FormUrlEncoded
    @POST("/login.php")
    public void loginUser(@Field("email") String email,
                             @Field("password") String password,
                             Callback<LoginResponse> responseCallback);

    @FormUrlEncoded
    @POST("/get_users.php")
    public void getUser(@Field("unique_id") String  uniqueid,
            Callback<GetRegisteredUsers> responseCallback);

    @FormUrlEncoded
    @POST("/updatetoken.php")
    public void updateToken(@Field("email") String  email,
                            @Field("token") String  token,
                        Callback<RegisterResponse> responseCallback);

    @FormUrlEncoded
    @POST("/TestNotification.php")
    public void sendMessage(@Field("title") String title,
                            @Field("sender") String sender,
                          @Field("messageBody") String messagebody,
                            @Field("SendTo") String token,
                          Callback<ChatResponse> responseCallback);

}
