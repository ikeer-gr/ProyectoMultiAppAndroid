package com.example.ikergomezrubio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
    }

    public void ejecutarContador(View v) {
        Intent i = new Intent(this, MainActivity.class);
        EditText valor = findViewById(R.id.valorinicial);

        int v1 = 0; // Valor predeterminado
        try {
            v1 = Integer.parseInt(valor.getText().toString()); // Capturar valor del EditText
        } catch (NumberFormatException e) {
            // Si el valor no es un número válido, se mantiene el predeterminado (0)
        }

        i.putExtra("valor", v1); // Enviar valor al Intent
        startActivity(i);
    }

    public void ejecutarCalculadora(View v) {
        Intent i = new Intent(this, calculadora.class);
        startActivity(i);
    }

    public void salir(View v) {
        finish();
    }
}
