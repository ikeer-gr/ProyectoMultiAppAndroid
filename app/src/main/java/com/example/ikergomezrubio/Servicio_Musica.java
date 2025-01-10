package com.example.ikergomezrubio;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Servicio_Musica extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    MediaPlayer miReproductor;

    public void onCreate() {
        super.onCreate();
        miReproductor = MediaPlayer.create(this, R.raw.cancion);
        miReproductor.setLooping(true);
        miReproductor.setVolume(100000, 100000);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        miReproductor.start();
        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        if (miReproductor.isPlaying()) {
            miReproductor.stop();
        }

        miReproductor.release();
        miReproductor = null;
    }
}
