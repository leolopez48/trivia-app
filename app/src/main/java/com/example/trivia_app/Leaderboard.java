package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trivia_app.Adaptadores.AdapterScoreboard;
import com.example.trivia_app.Models.Scoreboard;
import com.example.trivia_app.Models.Token;
import com.example.trivia_app.interfaces.InfoUsuarioApi;
import com.example.trivia_app.interfaces.LeaderboardApi;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Leaderboard extends AppCompatActivity {
    ImageView btnatras;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        progressDialog=new ProgressDialog(Leaderboard.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        getLeaderboard();
        btnatras=findViewById(R.id.atrasLeaderboard);

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 =new Intent(Leaderboard.this, MainActivity.class);
                startActivity(act2);
            }
        });

    }


    public void getLeaderboard(){


        // Creating the api client instance
        LeaderboardApi leaderApi = new ApiClient().getClient().create(LeaderboardApi.class);

        // Calling the api
        Call<List<Scoreboard>> call = leaderApi.getScoreboard("Bearer " + Token.VALOR);

        call.enqueue(new Callback<List<Scoreboard>>() {
            @Override
            public void onResponse(Call<List<Scoreboard>> call, Response<List<Scoreboard>> response) {
                if(!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Leaderboard.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Toast.makeText(Leaderboard.this, Token.VALOR, Toast.LENGTH_SHORT).show();
                //Toast.makeText(Leaderboard.this, new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();
                //Aqui se añade la informacion que trae la respuesta a nuestra lista de Categorias.
                List<Scoreboard> listScoreboard = response.body();

                //Aca recorremos en un for y seteamos cada uno de los items de nuestra Lista en el adaptador
               for (int i = 0; i < listScoreboard.size(); i++) {
                    RecyclerView rec= (RecyclerView) findViewById(R.id.rcvLeaderboard);
                    AdapterScoreboard myAdapter=new AdapterScoreboard(getBaseContext(),listScoreboard);
                    //al recyclerview le asignamos un grid de 1 que viene siendo para mostrar solo uno por fila
                    rec.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    //Esto añade una division entre cada item
                    rec.addItemDecoration(new DividerItemDecoration(rec.getContext(), DividerItemDecoration.VERTICAL));
                    rec.setAdapter(myAdapter);
                }

               progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Scoreboard>> call, Throwable t) {
                Toast.makeText(Leaderboard.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


}