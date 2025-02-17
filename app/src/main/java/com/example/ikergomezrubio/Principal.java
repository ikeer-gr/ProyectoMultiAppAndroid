package com.example.ikergomezrubio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Principal extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
        toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name6));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu mimenu) {
        getMenuInflater().inflate(R.menu.menu_principal, mimenu);
        return true;
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
        Intent i = new Intent(this, Calculadora.class);
        startActivity(i);
    }

    public void ejecutarMjuegos(View v) {
        Intent i = new Intent(this, MenuJuegos.class);
        startActivity(i);
    }

    public void ejecutarherramientas(View v) {
        Intent i = new Intent(this, Herramientas.class);
        startActivity(i);
    }

    public void ejecutarContactos(View v) {
        Intent i = new Intent(this, Contactos.class);
        startActivity(i);
    }

    public void ejecutarPodometro(View v) {
        Intent i = new Intent(this, Podometro.class);
        startActivity(i);
    }

    public void ejecutarTiempo(View v) {
        Intent i = new Intent(this, AppTiempo.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem option_menu) {
        int id = option_menu.getItemId();
        if (id == R.id.contador_menu) {
            ejecutarContador(null);
            return true;
        }
        if (id == R.id.calculadora_menu) {
            ejecutarCalculadora(null);
            return true;
        }
        if (id == R.id.salir_menu) {
            salir(null);
            return true;
        }
        if (id == R.id.juegos_menu) {
            ejecutarMjuegos(null);
            return true;
        }
        return super.onOptionsItemSelected(option_menu);
    }

    public void salir(View v) {
        finish();
    }
}
