package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia_app.Models.Data;
import com.example.trivia_app.Models.LoginM;
import com.example.trivia_app.Models.Token;
import com.example.trivia_app.interfaces.RespondApi;
import com.example.trivia_app.interfaces.ScoreboardApi;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Scoreboard extends AppCompatActivity {
    TextView txtscore, txtCorrectAnswer,txtWrongAnswer,txtTituloConNombre;
    Button btnLeaderboard, btnHome;
    public int score=0;
    public int user_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        txtscore=findViewById(R.id.txtPuntuacion);
        txtCorrectAnswer=findViewById(R.id.txtanswerBuenas);
        txtWrongAnswer=findViewById(R.id.txtMalas);
        txtTituloConNombre=findViewById(R.id.txtTituloScoreboard);
        btnLeaderboard=findViewById(R.id.btnRankingScore);
        btnHome=findViewById(R.id.btnHomeScore);



        //Recibir info
        Intent intent=getIntent();
        score= intent.getExtras().getInt("score");
        user_id= intent.getExtras().getInt("user_id");
        String correct=intent.getExtras().getString("correct");
        int wrong=intent.getExtras().getInt("wrong");
        String total=intent.getExtras().getString("total");
        String message=intent.getExtras().getString("message");

        txtscore.setText(""+score);
        txtCorrectAnswer.setText(correct);
        txtWrongAnswer.setText(""+wrong);
        txtTituloConNombre.setText("¡Bien Hecho!");

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act1 =new Intent(Scoreboard.this,MainActivity.class);
                startActivity(act1);
                addScoreboard(score,user_id);
            }
        });

        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act1 =new Intent(Scoreboard.this,Leaderboard.class);
                startActivity(act1);
                addScoreboard(score,user_id);
            }
        });

    }

    public void addScoreboard(int score, int user_id) {
        // Creating the api client instance
        ScoreboardApi scoreboardApi = ApiClient.retrofit.create(ScoreboardApi.class);
        // Calling the api
        Call<Data> call = scoreboardApi.addScoreboard("Bearer " + Token.VALOR, score,user_id);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                try{
                    if(response.isSuccessful()) {

                        // Retrieving the api response
                        Data data = response.body();

                            //Toast.makeText(Scoreboard.this, new Gson().toJson(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                        }

                } catch (Exception ex) {
                    Toast.makeText(Scoreboard.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(Scoreboard.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (shouldAllowBack()) {
            super.onBackPressed();
        } else {

        }
    }

    private boolean shouldAllowBack() {
        return false;
    }



}