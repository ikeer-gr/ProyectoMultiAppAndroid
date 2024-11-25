package com.example.ikergomezrubio;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int contador = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se eliminó la llamada a EdgeToEdge porque no es relevante a menos que esté explícitamente configurada en tu proyecto.
        setContentView(R.layout.activity_main);
        mostrarResultado();

        // Configuración del listener de teclado
        TextView reseteo = findViewById(R.id.textDefault);
        reseteo.setOnEditorActionListener(new EventoTeclado());

        // Verificar que el Intent contiene datos antes de acceder a ellos
        Bundle datos = getIntent().getExtras();
        if (datos != null && datos.containsKey("d1")) {
            contador = datos.getInt("d1", 5); // Valor por defecto es 5
        }
        mostrarResultado();
    }

    // Evento para manejar la acción del teclado
    private class EventoTeclado implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                reseteo(v); // Se pasa 'v' para evitar posibles null pointer exceptions
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

        // Ocultar teclado después de reiniciar
        if (v != null) { // Verificar que 'v' no sea nulo
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


    //    @Override
//    public void onPause() {
//        super.onPause();
//        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = datos.edit();
//        editor.putInt("cuenta", contador);
//        editor.apply();
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
//        contador = datos.getInt("cuenta", 0);
//        Log.d(msg, "onResume contador" + contador);
//        mostrarResultado();
//    }
//
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("He pasado de sesion");
    }
}
