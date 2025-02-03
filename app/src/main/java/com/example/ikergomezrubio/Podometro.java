package com.example.ikergomezrubio;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Podometro extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private TextView tvStepsTaken;

    // Variables para el algoritmo de detección de pasos
    private int stepsCount = 0;
    private final float[] gravity = new float[3];  // Inicialmente en 0: {0, 0, 0}
    private final float ALPHA = 0.8f;          // Constante para el filtro de paso bajo
    private static final float STEP_THRESHOLD = 2.0f;  // Umbral para detectar un paso
    private long lastStepTime = 0;
    private static final long STEP_DELAY_NS = 250000000; // 250 ms en nanosegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podometro);
        tvStepsTaken = findViewById(R.id.tv_stepsTaken);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometerSensor == null) {
                Toast.makeText(this, "El acelerómetro no está disponible en este dispositivo", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometerSensor != null) {
            // Puedes probar con SENSOR_DELAY_UI o SENSOR_DELAY_GAME para mayor frecuencia de actualización
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d("Podometro", "Acelerómetro: x=" + event.values[0] +
                    ", y=" + event.values[1] +
                    ", z=" + event.values[2]);

            // Aplicar filtro de paso bajo para extraer la componente de gravedad
            gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * event.values[0];
            gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * event.values[1];
            gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * event.values[2];

            // Obtener la aceleración lineal eliminando la gravedad
            float linearAccelerationX = event.values[0] - gravity[0];
            float linearAccelerationY = event.values[1] - gravity[1];
            float linearAccelerationZ = event.values[2] - gravity[2];

            // Calcular la magnitud de la aceleración lineal
            float magnitude = (float) Math.sqrt(
                    linearAccelerationX * linearAccelerationX +
                            linearAccelerationY * linearAccelerationY +
                            linearAccelerationZ * linearAccelerationZ
            );

            Log.d("Podometro", "Magnitud: " + magnitude);

            // Si la magnitud supera el umbral y ha pasado el retardo mínimo, se cuenta un paso
            if (magnitude > STEP_THRESHOLD) {
                long currentTime = System.nanoTime();
                if (currentTime - lastStepTime > STEP_DELAY_NS) {
                    stepsCount++;
                    lastStepTime = currentTime;
                    tvStepsTaken.setText(String.valueOf(stepsCount));
                    Log.d("Podometro", "Paso detectado. Total pasos: " + stepsCount);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario implementar para este ejemplo
    }
}
