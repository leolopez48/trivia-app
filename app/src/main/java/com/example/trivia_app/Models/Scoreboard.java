package com.example.trivia_app.Models;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class Scoreboard {
    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String user_id;
    @SerializedName("score")
    private int score;

    public Scoreboard(int id, String user_id, int score) {
        this.id = id;
        this.user_id = user_id;
        this.score = score;
    }

    public Scoreboard() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
