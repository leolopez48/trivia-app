package com.example.trivia_app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trivia_app.Models.LoginM;

import java.util.List;

@Dao
public interface AuthDao {
    @Query("SELECT * FROM loginM")
    List<LoginM> getAll();
    @Insert
    Long insert(LoginM auth);
}
