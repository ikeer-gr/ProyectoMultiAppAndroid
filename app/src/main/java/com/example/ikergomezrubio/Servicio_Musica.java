package com.example.ikergomezrubio;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

public class Servicio_Musica extends Service {

    private MediaPlayer miReproductor;
    private ArrayList<Integer> playlist;
    private int indexCancion = 0;

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "canal_musica";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Crea el canal de notificación si es necesario (Android Oreo o superior)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Canal de Música",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        // Inicializamos la lista de canciones
        playlist = new ArrayList<>();
        playlist.add(R.raw.cancion1); // Reemplaza por el ID de tus recursos
        playlist.add(R.raw.cancion2);
        playlist.add(R.raw.cancion3);
        // Puedes añadir más canciones según lo necesites

        // Iniciamos el reproductor con la primera canción de la lista
        iniciarCancion(indexCancion);
    }

    // Método para inicializar el reproductor con una canción específica
    private void iniciarCancion(int index) {
        miReproductor = MediaPlayer.create(this, playlist.get(index));

        if (miReproductor == null) {
            Log.e("Servicio_Musica", "Error al crear MediaPlayer para el recurso: " + playlist.get(index));
            return;
        }

        miReproductor.setVolume(1.0f, 1.0f);  // Ajusta el volumen si es necesario
        miReproductor.setLooping(false); // No repetirá la misma canción

        // Listener para detectar cuando finaliza la canción y pasar a la siguiente
        miReproductor.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Liberamos el reproductor actual
                miReproductor.release();
                indexCancion++;
                if (indexCancion < playlist.size()) {
                    iniciarCancion(indexCancion);
                    if (miReproductor != null) {
                        miReproductor.start();
                        Log.d("Servicio_Musica", "Iniciando canción índice " + indexCancion);
                    }
                } else {
                    // Reiniciamos la lista de canciones
                    indexCancion = 0;
                    iniciarCancion(indexCancion);
                    if (miReproductor != null) {
                        miReproductor.start();
                        Log.d("Servicio_Musica", "Reiniciando playlist");
                    }
                    // O bien detener el servicio con stopSelf();
                }
            }
        });

        Log.d("Servicio_Musica", "MediaPlayer inicializado para la canción índice " + index);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Creamos la notificación persistente
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.radioen) // Asegúrate de tener este recurso o cámbialo por uno existente
                .setContentTitle("Reproduciendo música")
                .setContentText("La música sigue sonando")
                .build();

        // Inicia el servicio en primer plano
        startForeground(NOTIFICATION_ID, notification);

        // Si el reproductor no está iniciado, se inicia la reproducción
        if (miReproductor != null && !miReproductor.isPlaying()) {
            miReproductor.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (miReproductor != null) {
            if (miReproductor.isPlaying()) {
                miReproductor.stop();
            }
            miReproductor.release();
            miReproductor = null;
        }
    }
}
