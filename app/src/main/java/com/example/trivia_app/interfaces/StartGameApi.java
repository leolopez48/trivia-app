package com.example.trivia_app.interfaces;

import com.example.trivia_app.Models.LoginM;
import com.example.trivia_app.Models.Scoreboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StartGameApi {
    @POST("api/game/start")
    // @Body es para post, @path para get
    public Call<LoginM> startGame(@Header("Authorization") String token);

}
