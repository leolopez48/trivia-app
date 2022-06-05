package com.example.trivia_app.interfaces;

import com.example.trivia_app.Models.Info;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UpdateUserApi {

    @POST("api/game/updateUserInfo")

    public Call<Info> update(@Header("Authorization") String token , @Query("name") String username,
                             @Query("email") String email,
                             @Query("password") String password ,
                             @Query("id") int user_id);
}
