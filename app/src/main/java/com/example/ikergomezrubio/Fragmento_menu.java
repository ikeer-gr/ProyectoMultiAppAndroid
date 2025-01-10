package com.example.ikergomezrubio;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class Fragmento_menu extends Fragment {
    private final int[] botonesmenu = {R.id.b_linterna, R.id.b_nivel, R.id.b_musica, R.id.b_salir};

    public Fragmento_menu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mimenu = inflater.inflate(R.layout.fragment_fragmento_menu, container, false);
        Button botonmenu;

        for (int i = 0; i < botonesmenu.length; i++) {
            botonmenu = mimenu.findViewById(botonesmenu[i]);
            final int queBoton = i;
            botonmenu.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Activity estaActividad = getActivity();
                    ((ComunicaMenu) estaActividad).menu(queBoton);
                }
            });
        }
        return mimenu;
    }
}