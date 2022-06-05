package com.example.trivia_app.interfaces;

import com.example.trivia_app.Models.Register;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RegisterApi {
    @POST("api/auth/register")

    public Call<Register> register(@Header("Authorization") String token , @Query("name") String username,
                               @Query("email") String email, @Query("password") String password );
}
