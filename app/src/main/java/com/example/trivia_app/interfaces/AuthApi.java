package com.example.trivia_app.interfaces;

import com.example.trivia_app.Models.LoginM;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthApi {
    @POST("api/auth/login")
    // @Body es para post, @path para get
    public Call<LoginM> login(@Body LoginM login);

}
