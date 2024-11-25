package com.example.ikergomezrubio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
    }

    public void ejecutarContador(View v) {
        Intent i = new Intent(this, MainActivity.class);
        EditText valor= (EditText) findViewById(R.id.valorinicial);
        int v1 = Integer.parseInt(valor.getText().toString());
        i.putExtra("valor", v1);
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