package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia_app.Models.Answer;
import com.example.trivia_app.Models.Data;
import com.example.trivia_app.Models.Game;
import com.example.trivia_app.Models.LoginM;
import com.example.trivia_app.Models.Question;
import com.example.trivia_app.Models.Token;
import com.example.trivia_app.interfaces.InfoUsuarioApi;
import com.example.trivia_app.interfaces.QuestionApi;
import com.example.trivia_app.interfaces.RespondApi;
import com.example.trivia_app.interfaces.StartGameApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quiz extends AppCompatActivity {
    TextView txtCategoria, txtpregunta,timerText,txtScore,txtPrueba,txtnumPreguntas,txtSalir;
    Button btnOpcion1,btnOpcion2,btnOpcion3,btnOpcion4,btnSumbit;

    private Button[] btn = new Button[4];
    public ImageView[] vidasimg = new ImageView[3];
    public Button btn_unfocus;
    public int[] btn_id = {R.id.btnOpcion1, R.id.btnOpcion2, R.id.btnOpcion3, R.id.btnOpcion4};
    public int[] vidasid = {R.id.life1img, R.id.life1img, R.id.life1img};
    public String answer="";
    public static int question_id=0;
    public static String selectedOptionByUser="";
    public long timeleftinMiliseconds=60000;
    public CountDownTimer timer;
    public Button btn_focus;

    //Inicializamos las listas
    public List<Answer> datalistAnswers = new ArrayList<>();
    public List<Question> datalistQuestion = new ArrayList<>();
    public List<Game> datalistGame = new ArrayList<>();
    public int puntuacion=0;
    public int j=0;
    public int nQuestion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        timerText=findViewById(R.id.timer1);
        txtScore=findViewById(R.id.txtScore);
        txtnumPreguntas=findViewById(R.id.txtstatusPreguntas);
        txtCategoria=findViewById(R.id.txtCategoria);
        txtpregunta=findViewById(R.id.textPregunta);
        btnOpcion1=findViewById(R.id.btnOpcion1);
        btnOpcion2=findViewById(R.id.btnOpcion2);
        btnOpcion3=findViewById(R.id.btnOpcion3);
        btnOpcion4=findViewById(R.id.btnOpcion4);
        btnSumbit=findViewById(R.id.btnSumbit);
        txtSalir=findViewById(R.id.txtsalir);
        txtPrueba=findViewById(R.id.txtprueba);

        //setear botones default colores
        for(int i = 0; i < btn.length; i++){
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Or use switch
                    switch (v.getId()){
                        case R.id.btnOpcion1:
                            setFocus(btn_unfocus, btn[0]);
                            Button a = (Button)findViewById(R.id.btnOpcion1);
                            selectedOptionByUser = a.getText().toString();

                            break;

                        case R.id.btnOpcion2 :
                            setFocus(btn_unfocus, btn[1]);
                            Button b = (Button)findViewById(R.id.btnOpcion2);
                            selectedOptionByUser = b.getText().toString();

                            break;

                        case R.id.btnOpcion3 :
                            setFocus(btn_unfocus, btn[2]);
                            Button c = (Button)findViewById(R.id.btnOpcion3);
                            selectedOptionByUser = c.getText().toString();

                            break;

                        case R.id.btnOpcion4 :
                            setFocus(btn_unfocus, btn[3]);
                            Button d= (Button)findViewById(R.id.btnOpcion4);
                            selectedOptionByUser = d.getText().toString();

                            break;
                    }
                }

            });
        }
        btn_unfocus = btn[0];

        //Recibir info
        Intent intent=getIntent();
        String nombreCategoria=intent.getExtras().getString("tituloCategoria");

        txtCategoria.setText(nombreCategoria);
        starGame();
        next();

        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()){
                    Toast.makeText(Quiz.this, "Seleccione una opción", Toast.LENGTH_SHORT).show();
                }else{
                    mostrarBuenas();
                    mostrarMalas();

                    btnSumbit.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            respond(question_id,selectedOptionByUser);
                        }
                    }, 420);
                }

            }
        });

        txtSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Quiz.this);

                builder.setTitle("¿Desea volver al menú?");
                builder.setMessage("No se guardara el resultado actual");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do something
                        //Toast.makeText(getApplicationContext(),"Se cerro sesión",Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        Intent act2 =new Intent(Quiz.this, MainActivity.class);
                        startActivity(act2);
                        overridePendingTransition(0, 0);
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


    }

    public void startTimer(){
        timer= new CountDownTimer(timeleftinMiliseconds,1000) {
            @Override
            public void onTick(long l) {
                timeleftinMiliseconds= l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void updateTimer(){
        int seconds=(int) timeleftinMiliseconds %60000/1000;
        String timeLeftText="";
        if(seconds<10) timeLeftText+="0";
        timeLeftText+=seconds;
        timerText.setText(timeLeftText);
        if (seconds==0)
        {
            Toast.makeText(Quiz.this, "Se te agoto el tiempo, siguiente pregunta", Toast.LENGTH_SHORT).show();
            timer.cancel();
            timerText.setText("60");
            timeleftinMiliseconds=60000;
            //next();

        }
    }

    private void setFocus(Button btn_unfocus, Button btn_focus) {
        btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
        this.btn_unfocus = btn_focus;
    }

    public void starGame() {
        // Creating the api client instance
        StartGameApi startGameApi = new ApiClient().getClient().create(StartGameApi.class);
        // Calling the api
        Call<LoginM> call = startGameApi.startGame("Bearer " + Token.VALOR);

        call.enqueue(new Callback<LoginM>() {
            @Override
            public void onResponse(Call<LoginM> call, Response<LoginM> response) {
                try{
                    if(response.isSuccessful()) {
                        LoginM model = response.body();
                        Toast.makeText(Quiz.this, new Gson().toJson(response.body().getMessage().toString()), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(Quiz.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginM> call, Throwable t) {
                Toast.makeText(Quiz.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void next() {

        // Creating the api client instance
        QuestionApi questionApi = new ApiClient().getClient().create(QuestionApi.class);
        // Calling the api
        Call<Data> call = questionApi.next("Bearer " + Token.VALOR, txtCategoria.getText().toString());

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                try{
                    if(response.isSuccessful()) {
                        //startTimer();
                        // Retrieving the api response
                        Data data = response.body();

                        //Asignamos
                        datalistAnswers= data.getAnswers();
                        datalistQuestion= Collections.singletonList(data.getQuestion());
                        datalistGame= Collections.singletonList(data.getGame());

                        //recorrer las respuestas y con un if ver cual tiene YES en el atributo de answercorrect
                       for(int i = 0; i < datalistAnswers.size(); i++) {
                            if(datalistAnswers.get(i).getAnswerCorrect().equals("Yes")){
                                answer = datalistAnswers.get(i).getAnswerName();
                                //question_id=datalistAnswers.get(i).getQuestionId();
                            }
                        }

                        txtpregunta.setText(datalistQuestion.get(0).getQuestionName());
                        btnOpcion1.setText(datalistAnswers.get(0).getAnswerName());
                        btnOpcion2.setText(datalistAnswers.get(1).getAnswerName());
                        btnOpcion3.setText(datalistAnswers.get(2).getAnswerName());
                        btnOpcion4.setText(datalistAnswers.get(3).getAnswerName());
                        question_id=datalistAnswers.get(0).getQuestionId();

                        j++;
                        nQuestion=data.getTotal();
                        txtnumPreguntas.setText(""+j+" / "+nQuestion);

                        selectedOptionByUser = "";
                        // Toast.makeText(Quiz.this, new Gson().toJson(datalistAnswers), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(Quiz.this, "Seleccione una respuesta", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(Quiz.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void respond(int question_id1, String answer2) {
        // Creating the api client instance
        RespondApi respondApi = new ApiClient().getClient().create(RespondApi.class);
        // Calling the api
        Call<Data> call = respondApi.next("Bearer " + Token.VALOR, question_id1,answer2);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                try{
                    if(response.isSuccessful()) {

                        // Retrieving the api response
                        Data data = response.body();
                        if(data.getGame().getLifesUsed()==3 | j==nQuestion)
                        {
                            if(data.getStatus().equals("error")){ puntuacion=puntuacion-300;}else{ puntuacion=puntuacion+1000;}
                            txtScore.setText(""+puntuacion);
                            int respuestasMalas2 = nQuestion-data.getGame().getAnswersCorrects();
                            Intent act2 =new Intent(Quiz.this,Scoreboard.class);
                            act2.putExtra("score",Integer.parseInt(txtScore.getText().toString()));
                            act2.putExtra("correct",data.getGame().getAnswersCorrects().toString());
                            act2.putExtra("total",data.getGame().getTotalAnswers().toString());
                            act2.putExtra("wrong",respuestasMalas2);
                            act2.putExtra("message",data.getMessage());
                            act2.putExtra("user_id",data.getGame().getUserId());
                            startActivity(act2);
                        } else{

                            if(data.getStatus().equals("error")){
                                puntuacion=puntuacion-300;
                            }else{
                                puntuacion=puntuacion+1000;
                            }
                            txtScore.setText(""+puntuacion);
                           // txtPrueba.setText("Resultado: "+data.getMessage()+"\nStatus: "+data.getStatus());
                            txtPrueba.setText(""+data.getMessage());

                            //Quita una imagen del icono de vida
                            for(int i = 0; i < data.getGame().getLifesUsed(); i++) {
                                vidasimg[i]=findViewById(R.id.life1img+i);
                                vidasimg[i].setVisibility(View.GONE);
                            }
                            //Pone los colores por default
                            reiniciarBotones();
                           // Toast.makeText(Quiz.this, "Datos enviados: "+question_id1 + "\nanswer: "+answer2, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(Quiz.this, new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();
                           //cambia pregunta.
                            next();
                        }

                    }
                } catch (Exception ex) {
                    Toast.makeText(Quiz.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(Quiz.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostrarBuenas() {
       String respuesta= answer;
       if(btnOpcion1.getText().toString().equals(respuesta)){
           //btnOpcion1.setBackgroundResource(R.drawable.rectangulo_respuestabuena);
           btnOpcion1.setTextColor(Color.GREEN);
       } else if(btnOpcion2.getText().toString().equals(respuesta)){
          // btnOpcion2.setBackgroundResource(R.drawable.rectangulo_respuestabuena);
           btnOpcion2.setTextColor(Color.GREEN);
       } else if(btnOpcion3.getText().toString().equals(respuesta)){
          // btnOpcion3.setBackgroundResource(R.drawable.rectangulo_respuestabuena);
           btnOpcion3.setTextColor(Color.GREEN);
       } else if(btnOpcion4.getText().toString().equals(respuesta)){
          // btnOpcion4.setBackgroundResource(R.drawable.rectangulo_respuestabuena);
           btnOpcion4.setTextColor(Color.GREEN);
       }
    }

    public void mostrarMalas() {
        String respuesta= answer;
        if(!btnOpcion1.getText().toString().equals(respuesta)){
            btnOpcion1.setTextColor(Color.RED);
        }
        if(!btnOpcion2.getText().toString().equals(respuesta)){
            btnOpcion2.setTextColor(Color.RED);
        }
        if(!btnOpcion3.getText().toString().equals(respuesta)){
            btnOpcion3.setTextColor(Color.RED);
        }
        if(!btnOpcion4.getText().toString().equals(respuesta)){
            btnOpcion4.setTextColor(Color.RED);
        }
    }

    public void reiniciarBotones(){
        btnOpcion1.setTextColor(Color.rgb(49, 50, 51));
        btnOpcion1.setBackgroundColor(Color.rgb(207, 207, 207));

        btnOpcion2.setTextColor(Color.rgb(49, 50, 51));
        btnOpcion2.setBackgroundColor(Color.rgb(207, 207, 207));

        btnOpcion3.setTextColor(Color.rgb(49, 50, 51));
        btnOpcion3.setBackgroundColor(Color.rgb(207, 207, 207));

        btnOpcion4.setTextColor(Color.rgb(49, 50, 51));
        btnOpcion4.setBackgroundColor(Color.rgb(207, 207, 207));
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