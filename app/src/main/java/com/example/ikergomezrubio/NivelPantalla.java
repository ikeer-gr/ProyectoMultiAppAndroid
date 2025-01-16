package com.example.ikergomezrubio;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;


    public class NivelPantalla extends AppCompatImageView {
        int posLeft, posTop, posXY, dimenw, dimenh, radio;
        Bitmap fondo,burbuja;

        public NivelPantalla(Context contexto) {
            super(contexto);
            posLeft = 100;
            posTop = 75;
            posXY = 500;
            radio = 200;
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) contexto).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            dimenw = metrics.widthPixels;
            dimenh = metrics.heightPixels;
            radio = dimenw / 2 - posLeft;
        }
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            setMeasuredDimension(dimenw,dimenh);
        }
        protected void onDraw(Canvas canvas) {
            // Crear un objeto Paint
            Paint lapiz = new Paint();
            // Dibujar el fondo en color gris
            lapiz.setColor(Color.GRAY);
            lapiz.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, dimenw, dimenh, lapiz);
            // Dibujar un círculo rojo con borde
            lapiz.setColor(Color.RED);
            lapiz.setStrokeWidth(15);
            lapiz.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(dimenw / 2, dimenh / 2, radio, lapiz);
            // Dibujar un círculo negro sólido
            lapiz.setColor(Color.BLACK);
            lapiz.setStyle(Paint.Style.FILL);
            canvas.drawCircle(dimenw / 2, dimenh / 2, radio, lapiz);
            // Dibujar un círculo rojo sólido más pequeño
            lapiz.setColor(Color.RED);
            lapiz.setStyle(Paint.Style.FILL);
            canvas.drawCircle(dimenw / 2, dimenh / 2, radio / 4, lapiz);
            // Dibujar líneas en cruz (horizontal y vertical)
            lapiz.setColor(Color.RED); // Asegurar color rojo
            lapiz.setStrokeWidth(15); // Grosor de las líneas
            // Línea horizontal
            canvas.drawLine(dimenw / 2 - radio, dimenh / 2, dimenw / 2 + radio, dimenh / 2, lapiz);
            // Línea vertical
            canvas.drawLine(dimenw / 2, dimenh / 2 - radio, dimenw / 2, dimenh / 2 + radio, lapiz);
            // Crear un trazado circular para agregar texto
            Path miTrazo = new Path();
            miTrazo.addCircle(dimenw / 2, dimenh / 2, radio, Path.Direction.CCW);
            // Configurar el Paint para el texto
            lapiz.setStyle(Paint.Style.FILL);
            lapiz.setStrokeWidth(5);
            lapiz.setTextSize(100);
            lapiz.setTypeface(Typeface.SANS_SERIF);
            lapiz.setColor(Color.RED); // Color para el texto
            // Dibujar el texto en el trazado circular
            canvas.drawTextOnPath("Mi nivel", miTrazo, dimenh, 100, lapiz);
        }

    }

