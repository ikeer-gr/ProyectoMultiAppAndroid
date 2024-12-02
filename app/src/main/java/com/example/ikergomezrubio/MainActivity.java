package com.example.ikergomezrubio;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int contador = 0; // Valor inicial por defecto
    private static final String estadoContador = "estadoContador"; // Clave para guardar estado en Bundle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView reseteo = findViewById(R.id.textDefault);
        reseteo.setOnEditorActionListener(new EventoTeclado());

        // Restaurar desde el Intent si se proporciona un valor
        if (savedInstanceState != null) {
            contador = savedInstanceState.getInt(estadoContador, 0); // Recuperar estado tras cambio de orientación
        } else {
            int valorDesdeIntent = getIntent().getIntExtra("valor", 0);
            if (valorDesdeIntent != 0) {
                contador = valorDesdeIntent; // Usar valor enviado desde principal
            } else {
                //SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
                contador = 0;//datos.getInt("cuenta", 0); // Restaurar desde SharedPreferences
            }
        }

        mostrarResultado();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(estadoContador, contador); // Guardar el valor actual del contador
    }

    private class EventoTeclado implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                reseteo(v);
            }
            return false;
        }
    }

    public void sumar(View j) {
        contador++;
        mostrarResultado();
    }

    public void restar(View c) {
        contador--;
        if (contador < 0) {
            CheckBox elemento = findViewById(R.id.checkBox);
            if (!elemento.isChecked()) {
                contador = 0;
            }
        }
        mostrarResultado();
    }

    public void reseteo(View v) {
        TextView resultado = findViewById(R.id.textDefault);
        try {
            contador = Integer.parseInt(resultado.getText().toString());
        } catch (NumberFormatException e) {
            Log.e("MainActivity", "Error al parsear número: " + e.getMessage());
            contador = 0;
        }
        mostrarResultado();

        if (v != null) {
            InputMethodManager teclado = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (teclado != null) {
                teclado.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    public void mostrarResultado() {
        TextView resultado = findViewById(R.id.valorContador);
        resultado.setText(String.valueOf(contador));
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = datos.edit();
        editor.putInt("cuenta", contador);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        mostrarResultado();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("He pasado de sesion");
    }
}
