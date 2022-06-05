package com.example.trivia_app.interfaces;

import com.example.trivia_app.Models.Data;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RespondApi {
    @POST("api/game/respond")

    public Call<Data> next(@Header("Authorization") String token , @Query("question_id") int question_id, @Query("answer")String answer);
}
