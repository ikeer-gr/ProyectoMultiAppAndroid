package com.example.ikergomezrubio;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class Fragmento_nivel extends Fragment implements SensorEventListener {

    private NivelPantalla pantalla;
    private int dimenw,dimenh;
    private SensorManager miManager;
    private Sensor miSensor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        miSensor = miManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        dimenw = metrics.widthPixels;
        dimenh = metrics.heightPixels;
        pantalla = new NivelPantalla(getActivity(),dimenh,dimenw);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento = pantalla;
        return fragmento;
    }

    public void onResume() {
        super.onResume();
        miManager.registerListener(this,miSensor,SensorManager.SENSOR_DELAY_GAME);

    }

    public void onPause() {
        super.onPause();
        miManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //pantalla.angulos(sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}