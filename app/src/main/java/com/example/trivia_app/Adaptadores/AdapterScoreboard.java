package com.example.trivia_app.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trivia_app.ApiClient;
import com.example.trivia_app.Leaderboard;
import com.example.trivia_app.Models.Categorias;
import com.example.trivia_app.Models.Scoreboard;
import com.example.trivia_app.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.List;
import java.util.Random;

public class AdapterScoreboard extends RecyclerView.Adapter<AdapterScoreboard.MyViewHolder>{

    private Context mcontext;
    private List<Scoreboard> mData;

    public AdapterScoreboard(Context mcontext, List<Scoreboard> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public AdapterScoreboard.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater= LayoutInflater.from(mcontext);
        view= mInflater.inflate(R.layout.cardview_leaderboard,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterScoreboard.MyViewHolder holder, int position) {

        Random rand = new Random();
        int randomNum = rand.nextInt((5 - 2) + 1) + 1;
        holder.nombre.setText(mData.get(position).getUser_id());
        //holder.score.setText(Integer.toString(mData.get(position).getScore()));
        holder.score.setText("Puntos: "+mData.get(position).getScore());
        holder.rank.setText("Rank "+(position+1));
        final String icono = randomNum+".png";

        Picasso.get()
                .load(ApiClient.BASE_URL+"img/"+icono)
                .into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView score;
        TextView rank;
        ImageView imgUser;
        CardView cardView;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            nombre= (TextView) itemView.findViewById(R.id.nombreUser);
            score= (TextView) itemView.findViewById(R.id.puntosUser);
            rank= (TextView) itemView.findViewById(R.id.rankUser);
            imgUser= (ImageView) itemView.findViewById(R.id.imgUser);
            cardView= (CardView) itemView.findViewById(R.id.cardviewL);
        }
    }

}
