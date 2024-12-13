package com.example.ikergomezrubio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class menu3raya extends AppCompatActivity {

    private String modoSeleccionado = "jugador_vs_maquina"; // Predeterminado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu3raya);
       // getSupportActionBar().setTitle(getString(R.string.app_name4));

        //configurarActionBar();

        CheckBox checkJvsJ = findViewById(R.id.checkJugadorVsJugador);
        CheckBox checkJvsM = findViewById(R.id.checkJugadorVsMaquina);
        Button btnConfirmar = findViewById(R.id.btnConfirmarModo);

        checkJvsM.setChecked(true); // Predeterminado

        checkJvsJ.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkJvsM.setChecked(false);
                modoSeleccionado = "jugador_vs_jugador";
            }
        });

        checkJvsM.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkJvsJ.setChecked(false);
                modoSeleccionado = "jugador_vs_maquina";
            }
        });

        btnConfirmar.setOnClickListener(v -> {
            if (checkJvsJ.isChecked() || checkJvsM.isChecked()) {
                iniciarJuego(modoSeleccionado);
            } else {
                Toast.makeText(this, "Por favor, selecciona un modo de juego", Toast.LENGTH_SHORT).show();
            }
        });
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
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Cierra la actividad y vuelve a la anterior
        return true;
    }

}
