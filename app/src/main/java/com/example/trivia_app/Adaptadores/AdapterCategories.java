package com.example.trivia_app.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trivia_app.ApiClient;
import com.example.trivia_app.Categories;
import com.example.trivia_app.MainActivity;
import com.example.trivia_app.Models.Categorias;
import com.example.trivia_app.Models.Token;
import com.example.trivia_app.Quiz;
import com.example.trivia_app.R;
import com.example.trivia_app.interfaces.categoriaApi;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolder> {

    private Context mcontext;
    private List<Categorias> mData;


    public AdapterCategories(Context mcontext, List<Categorias> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public AdapterCategories.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater= LayoutInflater.from(mcontext);
        view= mInflater.inflate(R.layout.cardview_categories,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategories.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titulo.setText(mData.get(position).getTitulo());
        final String icono = mData.get(position).getIcono();

        Picasso.get()
                .load( ApiClient.BASE_URL+"img/"+icono)
                .into(holder.imgCategoria);

        //click en el item
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(mcontext,Quiz.class);
                intent.putExtra("tituloCategoria",mData.get(position).getTitulo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        ImageView imgCategoria;
        CardView cardView;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            titulo= (TextView) itemView.findViewById(R.id.tituloCategoria);
            imgCategoria= (ImageView) itemView.findViewById(R.id.imagenCategoria);
            cardView= (CardView) itemView.findViewById(R.id.cardviewId);
        }
    }

}

