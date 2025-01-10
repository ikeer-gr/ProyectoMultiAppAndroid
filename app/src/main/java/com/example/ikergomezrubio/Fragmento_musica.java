package com.example.ikergomezrubio;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class Fragmento_musica extends Fragment {

    private boolean encendida;
    private ImageView botonMusica;

    public Fragmento_musica() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmento = inflater.inflate(R.layout.fragment_fragmento_musica, container, false);
        botonMusica = fragmento.findViewById(R.id.musica);

        if (encendida) {
            botonMusica.setImageResource(R.drawable.radioen);
        }

        botonMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (encendida) {
                    ApagarMusica();
                } else {
                    EnciendeMusica();
                }
            }
        });

        return fragmento;
    }


    public void ApagarMusica() {
        botonMusica.setImageResource(R.drawable.radioapagada);
        Intent miReproductor = new Intent(getActivity(), Servicio_Musica.class);
        getActivity().stopService(miReproductor);
        encendida = !encendida;
    }

    public void EnciendeMusica() {
        botonMusica.setImageResource(R.drawable.radioen);
        Intent miReproductor = new Intent(getActivity(), Servicio_Musica.class);
        getActivity().startService(miReproductor);
        encendida = !encendida;
    }
}