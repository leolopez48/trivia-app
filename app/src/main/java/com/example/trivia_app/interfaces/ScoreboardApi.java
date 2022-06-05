package com.example.trivia_app.interfaces;

import com.example.trivia_app.Models.Data;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ScoreboardApi {
    @POST("api/game/addScoreboard")

    public Call<Data> addScoreboard(@Header("Authorization") String token , @Query("score") int score, @Query("user_id") int user_id);
}
