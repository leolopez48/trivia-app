package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trivia_app.Adaptadores.AdapterCategories;
import com.example.trivia_app.Models.Categorias;
import com.example.trivia_app.interfaces.AuthApi;
import com.example.trivia_app.interfaces.InfoUsuarioApi;
import com.example.trivia_app.interfaces.categoriaApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Categories extends AppCompatActivity {
    public List<Categorias>istCategoria;
    ImageView btnregresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        btnregresar=  findViewById(R.id.atrasCategoria);
        getCategorias();

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 =new Intent(Categories.this,MainActivity.class);
                startActivity(act2);
            }
        });
}


public void getCategorias(){

    // Creating the api client instance aqui viene la ip inicializada
    categoriaApi catApi =  new ApiClient().getClient().create(categoriaApi.class);
    // Calling the api
    Call<List<Categorias>> call = catApi.getCategorias();

    call.enqueue(new Callback<List<Categorias>>() {
        @Override
        public void onResponse(Call<List<Categorias>> call, Response<List<Categorias>> response) {
            if(!response.isSuccessful()) {
                Toast.makeText(Categories.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                return;
            }
                //Aqui se añade la informacion que trae la respuesta a nuestra lista de Categorias.
                List<Categorias> listaCategorias = response.body();

                //Aca recorremos en un for y seteamos cada uno de los items de nuestra Lista en el adaptador
                for (int i = 0; i < listaCategorias.size(); i++) {
                    RecyclerView rec= (RecyclerView) findViewById(R.id.rcvCategorias);
                    AdapterCategories myAdapter=new AdapterCategories(getBaseContext(),listaCategorias);
                    rec.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                    rec.setAdapter(myAdapter);
                }

        }

        @Override
        public void onFailure(Call<List<Categorias>> call, Throwable t) {
            Toast.makeText(Categories.this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}
    //Elimina la posibilidad de darle el button atras del telefono.
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
