package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia_app.Models.Data;
import com.example.trivia_app.Models.Register;
import com.example.trivia_app.Models.Token;
import com.example.trivia_app.interfaces.QuestionApi;
import com.example.trivia_app.interfaces.RegisterApi;
import com.google.gson.Gson;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro extends AppCompatActivity {
    Button btnRegistro;
    TextView txtinicioSesion, txtNombreUsuario, txtCorreo, txtContraUsuario, txtContraRepetida ;
    String usuario, correo, contra, contraValidacion;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtinicioSesion=findViewById(R.id.txtInicioSesion);
        txtNombreUsuario=findViewById(R.id.txtNombreUsuario);
        txtCorreo=findViewById(R.id.txtCorreoL);
        txtContraUsuario=findViewById(R.id.txtContraUsuario);
        txtContraRepetida=findViewById(R.id.txtContraRepetida);
        btnRegistro=findViewById(R.id.btnRegistrarse);

        txtinicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                overridePendingTransition(0, 0);
                Intent act2 =new Intent(Registro.this, Login.class);
                startActivity(act2);
                overridePendingTransition(0, 0);
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario=txtNombreUsuario.getText().toString();
                correo=txtCorreo.getText().toString();
                contra=txtContraUsuario.getText().toString();
                contraValidacion=txtContraRepetida.getText().toString();

                if(usuario.isEmpty())
                    txtNombreUsuario.setError("Este campo es obligatorio");
                else if(correo.isEmpty())
                    txtCorreo.setError("Este campo es obligatorio");
                else if(contra.isEmpty())
                    txtContraUsuario.setError("Este campo es obligatorio");
                else if(contraValidacion.isEmpty())
                    txtContraRepetida.setError("Este campo es obligatorio");
                else if(!contra.equals(contraValidacion))
                    txtContraRepetida.setError("Las claves no coinciden");
                else{
                    progressDialog=new ProgressDialog(Registro.this);
                    progressDialog.show();
                    register(usuario,correo,contra);
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent);

                }

            }
        });

    }

    public void register( String username, String email, String password) {

        // Creating the api client instance
        RegisterApi registerApi = new ApiClient().getClient().create(RegisterApi.class);
        // Calling the api
        Call<Register> call = registerApi.register("Bearer " + Token.VALOR, username,email,password);

        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                try{
                    if(response.isSuccessful()) {
                        // Retrieving the api response
                        Register data = response.body();

                        if(data.getStatus().equals("success")){
                            progressDialog.dismiss();
                            overridePendingTransition(0, 0);
                            Intent act2 =new Intent(Registro.this, Login.class);
                            startActivity(act2);
                            overridePendingTransition(0, 0);
                        }

                        Toast.makeText(Registro.this, new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(Registro.this, "No se pudo registrar , intente de nuevo!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Registro.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}