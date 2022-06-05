package com.example.trivia_app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Game {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total_lifes")
    @Expose
    private Integer totalLifes;
    @SerializedName("lifes_used")
    @Expose
    private Integer lifesUsed;
    @SerializedName("total_answers")
    @Expose
    private Integer totalAnswers;
    @SerializedName("answers_corrects")
    @Expose
    private Integer answersCorrects;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalLifes() {
        return totalLifes;
    }

    public void setTotalLifes(Integer totalLifes) {
        this.totalLifes = totalLifes;
    }

    public Integer getLifesUsed() {
        return lifesUsed;
    }

    public void setLifesUsed(Integer lifesUsed) {
        this.lifesUsed = lifesUsed;
    }

    public Integer getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(Integer totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public Integer getAnswersCorrects() {
        return answersCorrects;
    }

    public void setAnswersCorrects(Integer answersCorrects) {
        this.answersCorrects = answersCorrects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
