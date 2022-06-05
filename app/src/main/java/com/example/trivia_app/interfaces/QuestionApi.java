package com.example.trivia_app.interfaces;


import com.example.trivia_app.Models.Data;
import com.example.trivia_app.Models.LoginM;
import com.example.trivia_app.Models.Question;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QuestionApi {

    @POST("api/game/nextQuestion")

    public Call<Data> next(@Header("Authorization") String token , @Query("category") String category);
}
