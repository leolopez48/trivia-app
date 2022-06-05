package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia_app.Models.Token;
import com.example.trivia_app.interfaces.AuthApi;
import com.example.trivia_app.Models.LoginM;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    TextView txtCorreo, txtContra, txtNuevoRegistro;
    Button btnLogin;
    String usuario;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCorreo=findViewById(R.id.txtCorreoL);
        txtContra=findViewById(R.id.txtContraL);
        btnLogin=findViewById(R.id.btnIniciarSesion);
        txtNuevoRegistro=findViewById(R.id.txtCrearCuenta);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= String.valueOf(txtCorreo.getText());
                String password= String.valueOf(txtContra.getText());
                //Validar cada elemento que no se encuentre vacio
                if(email.isEmpty())
                    txtCorreo.setError("Este campo es obligatorio");
                else if(password.isEmpty())
                    txtContra.setError("Es obligatorio");
                else{
                    progressDialog=new ProgressDialog(Login.this);
                    login(email, password);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );

                }


            }
        });

        txtNuevoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 =new Intent(Login.this,Registro.class);
                startActivity(act2);
            }
        });

    }


    private void login(String email, String password){
        // Creating the api client instance
        AuthApi authApi = new ApiClient().getClient().create(AuthApi.class);

        // Creating the login object
        LoginM request = new LoginM(email, password);

        // Calling the api
        Call<LoginM> call = authApi.login(request);


        call.enqueue(new Callback<LoginM>() {
            @Override
            public void onResponse(Call<LoginM> call, Response<LoginM> response) {
                try{
                    if(response.isSuccessful()) {
                        // Retrieving the api response
                        LoginM model = response.body();

                        //Toast.makeText(Login.this, new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();

                        Token.VALOR=response.body().getAccess_token();
                        Token.C=txtContra.getText().toString();
                        //Toast.makeText(Login.this, response.body().getAccess_token(), Toast.LENGTH_SHORT).show();

                        // Storing in the database
                        AppDatabase db = Room.databaseBuilder(
                                Login.this, AppDatabase.class,
                                "dbAuth").allowMainThreadQueries().build();
                        Long reg = db.authDAO().insert(model);
                        progressDialog.dismiss();


                        Intent act1 =new Intent(Login.this,MainActivity.class);
                        startActivity(act1);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Error: Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginM> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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