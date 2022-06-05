package com.example.trivia_app.interfaces;
import com.example.trivia_app.Models.Categorias;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface categoriaApi {

    @GET("api/auth/getCategorias")
    // @Body es para post, @path para get
    Call<List<Categorias>> getCategorias();
}
