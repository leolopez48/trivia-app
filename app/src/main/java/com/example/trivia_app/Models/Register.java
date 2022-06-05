package com.example.trivia_app.Models;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class Register {

    @SerializedName("name")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
