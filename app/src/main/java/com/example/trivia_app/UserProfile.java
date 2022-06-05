package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia_app.Models.Data;
import com.example.trivia_app.Models.Info;
import com.example.trivia_app.Models.LoginM;
import com.example.trivia_app.Models.Token;
import com.example.trivia_app.interfaces.AuthApi;
import com.example.trivia_app.interfaces.InfoUsuarioApi;
import com.example.trivia_app.interfaces.UpdateUserApi;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {
    TextView txtNombreUsuarioInfo,txtScore;
    EditText txtNombreUsuario,txtEmailUsuario,txtContraUsuario;
    Button btnactualizarUsuario;
    ProgressDialog progressDialog;
    ImageView atrasPanelUsuario;
    String usuario, correo, contra;
    public int user_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txtNombreUsuarioInfo=findViewById(R.id.txtNombreUsuarioInfo);
        txtScore=findViewById(R.id.txtHighestScore);
        txtNombreUsuario=findViewById(R.id.txtUsernameInfo);
        txtEmailUsuario=findViewById(R.id.txtEmailInfo);
        txtContraUsuario=findViewById(R.id.txtPasswordInfo);
        btnactualizarUsuario=findViewById(R.id.btnactualizarUsuario);
        atrasPanelUsuario=findViewById(R.id.atrasPanelUsuario);

        progressDialog=new ProgressDialog(UserProfile.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        InfoUsuario();

        atrasPanelUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 =new Intent(UserProfile.this,MainActivity.class);
                startActivity(act2);
            }
        });

        btnactualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario=txtNombreUsuario.getText().toString();
                correo=txtEmailUsuario.getText().toString();
                contra=txtContraUsuario.getText().toString();
                Token.C=txtContraUsuario.getText().toString();

                if(usuario.isEmpty())
                    txtNombreUsuario.setError("Este campo es obligatorio");
                else if(correo.isEmpty())
                    txtEmailUsuario.setError("Este campo es obligatorio");
                else if(contra.isEmpty())
                    txtContraUsuario.setError("Este campo es obligatorio");
                else{
                    updateUsuario(usuario,correo,contra,user_id);

                    //recargar la activity para ver la nueva info
                    overridePendingTransition(0, 0);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                }
            }
        });


    }


    private void InfoUsuario(){
        // Creating the api client instance
        InfoUsuarioApi infoApi = new ApiClient().getClient().create(InfoUsuarioApi.class);

        // Calling the api
        Call<Info> call = infoApi.infoUsario("Bearer "+Token.VALOR);

        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                try{
                    if(response.isSuccessful()) {
                        // Retrieving the api response
                        Info data = response.body();
                        //Toast.makeText(UserProfile.this, new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();

                        try {
                            txtScore.setText(data.getScore().toString());
                        }catch (Exception e){
                            txtScore.setText("0");
                        }
                        txtNombreUsuarioInfo.setText(data.getUser().getName());
                        txtNombreUsuario.setText(data.getUser().getName());
                        txtEmailUsuario.setText(data.getUser().getEmail());

                        txtContraUsuario.setText(Token.C);
                        user_id=data.getUser_id();
                        progressDialog.dismiss();

                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(UserProfile.this, "Problema para encontrar usuario", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(UserProfile.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateUsuario(String username, String email, String password, int id){
        // Creating the api client instance
        UpdateUserApi updateUserApi = new ApiClient().getClient().create(UpdateUserApi.class);

        // Calling the api
        Call<Info> call = updateUserApi.update("Bearer " + Token.VALOR, username,email,password,id);

        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                try{
                    if(response.isSuccessful()) {
                        // Retrieving the api response
                        Info data = response.body();

                        Toast.makeText(UserProfile.this, new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();


                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(UserProfile.this, "Problema para actualizar usuario", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(UserProfile.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}