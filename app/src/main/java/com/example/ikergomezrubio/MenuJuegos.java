package com.example.ikergomezrubio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MenuJuegos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_juegos);
      //  getSupportActionBar().setTitle(getString(R.string.app_name5));

        // configurarActionBar();
    }

    private void configurarActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // Habilitar la flecha
            getSupportActionBar().setTitle(getString(R.string.app_name5));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Finalizar la actividad actual y volver atrás
        return true;
    }

    public void ejecutar3enRaya(View v) {
        Intent i = new Intent(this, Menu3raya.class);
        startActivity(i);
    }

    public void ejecutarEncuentralaPareja(View v) {
        Intent i = new Intent(this, Puzzle.class);
        startActivity(i);
    }

}
