package com.example.ikergomezrubio;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class Fragmento_nivel extends Fragment {

    private NivelPantalla pantalla;
    private int dimenw,dimenh;

    public Fragmento_nivel() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        dimenw = metrics.widthPixels;
        dimenh = metrics.heightPixels;
        pantalla = new NivelPantalla(getActivity(),dimenw,dimenh);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento = pantalla;
        return fragmento;
    }
}