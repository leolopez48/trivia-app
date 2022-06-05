package com.example.trivia_app.interfaces;

import com.example.trivia_app.Models.Info;
import com.example.trivia_app.Models.LoginM;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InfoUsuarioApi {

    @GET("api/auth/me")
    // @Body es para post, @path para get
    public Call<Info> infoUsario(@Header("Authorization") String token);
}
