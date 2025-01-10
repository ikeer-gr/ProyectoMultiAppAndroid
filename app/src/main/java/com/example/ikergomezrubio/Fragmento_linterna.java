package com.example.ikergomezrubio;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class Fragmento_linterna extends Fragment {

    private ImageView botonCamara;
    private boolean encendida;

    public Fragmento_linterna() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento = inflater.inflate(R.layout.fragment_fragmento_linterna, container, false);
        botonCamara = fragmento.findViewById(R.id.linterna);

        if (encendida) {
            botonCamara.setImageResource(R.drawable.linernaencendida);
        } else {
            botonCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (encendida) {
                        botonApagaFlash();
                        encendida = false;
                    } else {
                        botonEnciendeFlash();
                        encendida = true;
                    }
                }
            });
        }
        return fragmento;
    }

    public void botonEnciendeFlash() {
        botonCamara.setImageResource(R.drawable.linernaencendida);
        Activity estaActividad = getActivity();
        ((ManejaFlashCamara) estaActividad).enciendeApaga(encendida);
    }

    public void botonApagaFlash() {
        botonCamara.setImageResource(R.drawable.linernaapagada);
        Activity estaActividad = getActivity();
        ((ManejaFlashCamara) estaActividad).enciendeApaga(encendida);
    }

}





