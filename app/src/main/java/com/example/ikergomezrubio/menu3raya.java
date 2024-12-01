package com.example.ikergomezrubio;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;

public class menu3raya extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu3raya);

        configurarActionBar();

        Button btnPvP = findViewById(R.id.btnJugadorVsJugador);
        Button btnPvM = findViewById(R.id.btnJugadorVsMaquina);

        btnPvP.setOnClickListener(v -> iniciarJuego("jugador_vs_jugador"));
        btnPvM.setOnClickListener(v -> iniciarJuego("jugador_vs_maquina"));

    }

    private void iniciarJuego(String modo) {
        Intent intent = new Intent(this, juego3raya.class);
        intent.putExtra("modo", modo);
        startActivity(intent);
    }

     private void configurarActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // Muestra la flecha de "Atrás"
            actionBar.setTitle("Tres en Raya"); // Título de la actividad
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Cierra la actividad y vuelve a la anterior
        return true;
    }


}
