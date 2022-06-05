package com.example.trivia_app;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.trivia_app.Models.LoginM;

@Database(entities = {LoginM.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AuthDao authDAO();
}
