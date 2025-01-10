package com.example.ikergomezrubio;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class herramientas extends AppCompatActivity implements ComunicaMenu, ManejaFlashCamara {
    private Fragment[] misFragmentos;
    private CameraManager MiCamara;
    private String idCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_herramientas);
        misFragmentos = new Fragment[3];
        misFragmentos[0] = new fragmento_linterna();
        misFragmentos[2] = new fragmento_musica();
        misFragmentos[1] = new fragmento_nivel();
        MiCamara = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            idCamara = MiCamara.getCameraIdList()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void enciendeApaga(boolean estadoFlash) {
        try {
            if (estadoFlash) {
                Toast.makeText(this, "Linterna apagada", Toast.LENGTH_SHORT).show();
                MiCamara.setTorchMode(idCamara, false);
            } else {
                Toast.makeText(this, "Linterna encendida", Toast.LENGTH_SHORT).show();
                MiCamara.setTorchMode(idCamara, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}