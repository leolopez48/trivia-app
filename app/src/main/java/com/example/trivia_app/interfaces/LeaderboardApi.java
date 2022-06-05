package com.example.trivia_app.interfaces;

import com.example.trivia_app.Models.Categorias;
import com.example.trivia_app.Models.Scoreboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface LeaderboardApi {
    @GET("api/game/getScoreboard")
    // @Body es para post, @path para get

    public Call<List<Scoreboard>> getScoreboard(@Header("Authorization") String token);

}
