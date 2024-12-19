package com.example.ikergomezrubio;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class herramientas extends AppCompatActivity implements ComunicaMenu {
    private Fragment[] misFragmentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_herramientas);
        misFragmentos = new Fragment[3];
        misFragmentos[0] = new fragmento_linterna();
        misFragmentos[2] = new fragmento_musica();
        misFragmentos[1] = new fragmento_nivel();

    }

    @Override
    public void menu(int queboton) {
        if (queboton == 3) {
            finish();
        } else {
            FragmentManager miManejador = getSupportFragmentManager();
            FragmentTransaction miTransaccion = miManejador.beginTransaction();
            miTransaccion.replace(R.id.cont_herramientas, misFragmentos[queboton]);
            miTransaccion.commit();
        }

    }
}