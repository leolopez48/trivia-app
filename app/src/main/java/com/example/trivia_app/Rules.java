package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;

import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class Rules extends AppCompatActivity {
    public FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        fragmentManager=getSupportFragmentManager();
        final PaperOnboardingFragment paperOnboardingFragment= PaperOnboardingFragment.newInstance(getData());
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, paperOnboardingFragment);
        fragmentTransaction.commit();
    }

    public ArrayList<PaperOnboardingPage> getData() {
        PaperOnboardingPage rule1= new PaperOnboardingPage("En general",
                "Trivia app consiste en cuestionarios de conocimiento general, en la cual tu escogerás el tema que más te interese, las preguntas mostradas estarán acorde al tema seleccionado",
                Color.WHITE, R.drawable.studying,R.drawable.heart);


        PaperOnboardingPage rule2= new PaperOnboardingPage("Preguntas",
                "Una vez empezado el juego se te mostrará las preguntas y tendrás 3 vidas, es decir, puedes equivocarte solo 3 veces, si no consumes todas las vidas y terminas todos los niveles, el juego concluirá.",
                Color.WHITE, R.drawable.learning,R.drawable.heart);

        PaperOnboardingPage rule3= new PaperOnboardingPage("Puntos",
                "Por cada pregunta correcta que obtengas se sumaran 100 puntos, en cambio sí pierdes una vida se te restaran 30 puntos, ¡haz tu mayor esfuerzo para obtener tu mejor puntuación y entrar en el ranking de los mejores!",
                Color.WHITE, R.drawable.winner,R.drawable.crowns);

        ArrayList<PaperOnboardingPage> elementos= new ArrayList<>();
        elementos.add(rule1);
        elementos.add(rule2);
        elementos.add(rule3);
        return elementos;
    }
}