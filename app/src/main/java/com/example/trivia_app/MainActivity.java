package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trivia_app.Models.Categorias;
import com.example.trivia_app.Models.LoginM;
import com.example.trivia_app.Models.Scoreboard;
import com.example.trivia_app.Models.Token;
import com.example.trivia_app.interfaces.AuthApi;
import com.example.trivia_app.interfaces.categoriaApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button btnEmpezar,btnRanking,btnReglas,btnUsuario;
    ImageView btnLogout, btnMusic;
    public boolean bandera= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEmpezar=findViewById(R.id.btnJugar);
        btnRanking=findViewById(R.id.btnRanking);
        btnLogout=findViewById(R.id.btnlogoutimg);
        btnMusic=findViewById(R.id.btnMusicON);
        btnUsuario=findViewById(R.id.btnUsuario);
        btnReglas=findViewById(R.id.btnReglas);

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent act2 =new Intent(MainActivity.this,Categories.class);
                startActivity(act2);
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent act2 =new Intent(MainActivity.this, Leaderboard.class);
                startActivity(act2);
            }
        });
        //Servicio de musica
        Intent svc=new Intent(this, MediaPlayerService.class);
        if(!Token.BANDERA){
            btnMusic.setImageResource(R.drawable.ic_baseline_music_off_24);
            stopService(svc);
        }else{
            btnMusic.setImageResource(R.drawable.ic_baseline_music_on_24);
            startService(svc);
        }
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bandera)
                {
                    btnMusic.setImageResource(R.drawable.ic_baseline_music_on_24);
                    bandera=false;
                    Token.BANDERA=true;
                    startService(svc);
                }else{
                    btnMusic.setImageResource(R.drawable.ic_baseline_music_off_24);
                    bandera=true;
                    Token.BANDERA=false;
                    stopService(svc);
                }
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("¿Desea cerrar sesión?");
                builder.setMessage("Se esta cerrando sesión , ¿Esta seguro?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do something
                        Toast.makeText(getApplicationContext(),"Se cerro sesión",Toast.LENGTH_SHORT).show();

                        overridePendingTransition(0, 0);
                        Intent act2 =new Intent(MainActivity.this, Login.class);
                        startActivity(act2);
                        overridePendingTransition(0, 0);
                        stopService(svc);
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent act2 =new Intent(MainActivity.this,UserProfile.class);
                startActivity(act2);
            }
        });

        btnReglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent act2 =new Intent(MainActivity.this, Rules.class);
                startActivity(act2);

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