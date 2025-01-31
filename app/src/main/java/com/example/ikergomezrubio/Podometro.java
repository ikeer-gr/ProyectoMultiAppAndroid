package com.example.ikergomezrubio;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Podometro extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean running = false;
    private float totalSteps = 0f;
    private float previousTotalSteps = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSensor();
        loadData();
    }

    private void initializeSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) {
            Toast.makeText(this, "Error initializing sensor manager", Toast.LENGTH_SHORT).show();
            return;
        }

        // Intentar obtener el sensor de pasos
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            // Si no hay sensor de pasos, usar acelerómetro como alternativa
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (stepSensor != null) {
                Toast.makeText(this, "Using accelerometer for step counting", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No compatible sensor found", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void start() {
        if (sensorManager == null || stepSensor == null) {
            Toast.makeText(this, "SensorManager not initialized or sensor not available", Toast.LENGTH_SHORT).show();
            return;
        }
        running = true;
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
        running = false;
    }

    public void resetSteps() {
        previousTotalSteps = totalSteps;
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("key1", previousTotalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        previousTotalSteps = sharedPreferences.getFloat("key1", 0f);
        Log.d("Podometro", "Loaded previous steps: " + previousTotalSteps);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event == null || event.values.length == 0) {
            return;
        }
        if (running) {
            totalSteps = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se requiere implementación para este ejemplo
    }
}
