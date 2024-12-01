package com.example.ikergomezrubio;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class juego3raya extends AppCompatActivity {

    private String[][] tablero = new String[3][3];
    private String turno = "X";
    private String dificultad = "fácil"; // Valor por defecto
    private IA ia;
    private boolean juegoTerminado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego3raya);

        configurarActionBar();
        configurarSelectorDificultad();
        inicializarTablero();
        configurarBotonReiniciar();

        ia = new IA();  // Inicializa IA con dificultad "fácil" por defecto
    }
    private void configurarSelectorDificultad() {
        CheckBox checkFacil = findViewById(R.id.checkFacil);
        CheckBox checkMedio = findViewById(R.id.checkMedio);
        CheckBox checkDificil = findViewById(R.id.checkDificil);
        Button btnConfirmar = findViewById(R.id.btnConfirmarDificultad);

        // Seleccionar "Fácil" por defecto
        checkFacil.setChecked(true);

        checkFacil.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkMedio.setChecked(false);
                checkDificil.setChecked(false);
                dificultad = "fácil";
            }
        });

        checkMedio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkFacil.setChecked(false);
                checkDificil.setChecked(false);
                dificultad = "medio";
            }
        });

        checkDificil.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkFacil.setChecked(false);
                checkMedio.setChecked(false);
                dificultad = "difícil";
            }
        });

        btnConfirmar.setOnClickListener(v -> {
            if (checkFacil.isChecked() || checkMedio.isChecked() || checkDificil.isChecked()) {
                Toast.makeText(this, "Dificultad seleccionada: " + dificultad, Toast.LENGTH_SHORT).show();
                ia = new IA(); // Re-inicializa IA con la nueva dificultad
            } else {
                Toast.makeText(this, "Por favor, selecciona una dificultad", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void mostrarSelectorDificultad() {
        String[] opciones = {"Fácil", "Medio", "Difícil"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar Dificultad")
                .setItems(opciones, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            dificultad = "fácil";
                            break;
                        case 1:
                            dificultad = "medio";
                            break;
                        case 2:
                            dificultad = "difícil";
                            break;
                    }
                })
                .setCancelable(false) // Evitar cerrar sin seleccionar
                .show();
    }

    private void inicializarTablero() {
        GridLayout gridLayout = findViewById(R.id.gridTablero);
        TextView tvTurno = findViewById(R.id.tvTurno);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button btn = (Button) gridLayout.getChildAt(i);
            btn.setText("");
            final int fila = i / 3;
            final int columna = i % 3;

            btn.setOnClickListener(v -> {
                if (!juegoTerminado && tablero[fila][columna] == null) {
                    realizarMovimientoJugador(fila, columna, btn);
                    tvTurno.setText("Turno: " + turno);
                }
            });
        }
    }

    private void realizarMovimientoJugador(int fila, int columna, Button btn) {
        tablero[fila][columna] = turno;
        btn.setText(turno);
        if (verificarGanador()) {
            mostrarGanador(turno);
        } else if (esEmpate()) {
            mostrarGanador("Empate");
        } else {
            cambiarTurno();
            if (turno.equals("O")) {
                realizarMovimientoIA();
            }
        }
        if (juegoTerminado) {
        }
    }

    private void realizarMovimientoIA() {
        int[] movimiento = ia.jugar(tablero, turno, dificultad);
        int fila = movimiento[0];
        int columna = movimiento[1];
        Button btn = (Button) ((GridLayout) findViewById(R.id.gridTablero)).getChildAt(fila * 3 + columna);
        realizarMovimientoJugador(fila, columna, btn);

    }

    private void cambiarTurno() {
        turno = turno.equals("X") ? "O" : "X";
    }

    private boolean verificarGanador() {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] != null && tablero[i][0].equals(tablero[i][1]) && tablero[i][1].equals(tablero[i][2]))
                return true;
            if (tablero[0][i] != null && tablero[0][i].equals(tablero[1][i]) && tablero[1][i].equals(tablero[2][i]))
                return true;
        }

        if (tablero[0][0] != null && tablero[0][0].equals(tablero[1][1]) && tablero[1][1].equals(tablero[2][2]))
            return true;
        return tablero[0][2] != null && tablero[0][2].equals(tablero[1][1]) && tablero[1][1].equals(tablero[2][0]);
    }

    private boolean esEmpate() {
        for (String[] fila : tablero) {
            for (String celda : fila) {
                if (celda == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void mostrarGanador(String ganador) {
        TextView tvGanador = findViewById(R.id.tvGanador);
        tvGanador.setVisibility(View.VISIBLE);
        tvGanador.setText(ganador.equals("Empate") ? "Empate" : "Ganador: " + ganador);
        juegoTerminado = true;
    }

    private void configurarBotonReiniciar() {
        Button btnReiniciar = findViewById(R.id.btnReiniciar);
        btnReiniciar.setOnClickListener(v -> reiniciarJuego());
    }

    private void reiniciarJuego() {
        tablero = new String[3][3];
        turno = "X";
        juegoTerminado = false;
        ia = new IA(); // Reinicia la IA con la última dificultad seleccionada

        GridLayout gridLayout = findViewById(R.id.gridTablero);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button btn = (Button) gridLayout.getChildAt(i);
            btn.setText("");
        }

        TextView tvGanador = findViewById(R.id.tvGanador);
        tvGanador.setVisibility(View.GONE);
    }
}
