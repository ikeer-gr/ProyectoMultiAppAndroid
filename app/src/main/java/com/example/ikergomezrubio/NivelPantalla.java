package com.example.ikergomezrubio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;


public class NivelPantalla extends AppCompatImageView {
    int posLeft, posTop, posXY, dimenw, dimenh, radio;
    Bitmap fondo, burbuja;
    private float[] angulos;

    public NivelPantalla(Context contexto, int dw, int dh) {
        super(contexto);
        dimenw = dw;
        dimenh = dh;
        posLeft = 100;
        posTop = 75;
        posXY = 500;
        radio = dimenh / 2 - posLeft;
        fondo = iniciaFondo();
        angulos = new float[2];
        angulos[0] = 0;
        angulos[1] = 0;
        BitmapDrawable bola = (BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.bola);
        burbuja = bola.getBitmap();
        burbuja = Bitmap.createScaledBitmap(burbuja, radio / 4, radio / 4, true);

    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        radio = dimenw / 2 - posLeft;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dimenw, dimenh);
    }
    protected Bitmap iniciaFondo() {
        Bitmap.Config conf = Bitmap.Config.ARGB_4444;
        Bitmap fondo = Bitmap.createBitmap(dimenw, dimenh, conf);
        Canvas canvas = new Canvas(fondo);

        Paint lapiz = new Paint();
        lapiz.setAntiAlias(true);

        // Fondo en gris
        lapiz.setColor(Color.GRAY);
        lapiz.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, dimenw, dimenh, lapiz);

        // Círculo rojo con borde
        lapiz.setColor(Color.RED);
        lapiz.setStrokeWidth(15);
        lapiz.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(dimenw / 2, dimenh / 2, radio, lapiz);

        // Círculo negro sólido
        lapiz.setColor(Color.BLACK);
        lapiz.setStyle(Paint.Style.FILL);
        canvas.drawCircle(dimenw / 2, dimenh / 2, radio, lapiz);

        // Círculo rojo sólido más pequeño
        lapiz.setColor(Color.RED);
        canvas.drawCircle(dimenw / 2, dimenh / 2, radio / 4, lapiz);

        // Líneas en cruz
        lapiz.setStrokeWidth(15);
        canvas.drawLine(dimenw / 2 - radio, dimenh / 2, dimenw / 2 + radio, dimenh / 2, lapiz);
        canvas.drawLine(dimenw / 2, dimenh / 2 - radio, dimenw / 2, dimenh / 2 + radio, lapiz);

        // Trazado circular para el texto
        Path miTrazo = new Path();
        miTrazo.addCircle(dimenw / 2, dimenh / 2, radio, Path.Direction.CCW);

        // Configuración del texto
        lapiz.setStyle(Paint.Style.FILL);
        lapiz.setStrokeWidth(5);
        lapiz.setTextSize(100);
        lapiz.setTypeface(Typeface.SANS_SERIF);
        lapiz.setColor(Color.RED);

        // Dibuja el texto en el trazado
        canvas.drawTextOnPath("Mi nivel", miTrazo, dimenh,100, lapiz);


        return fondo;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(fondo, 0, 0, null);
        int posX = radio + (int) (angulos[0] / 10 * radio) + posLeft / 2;
        int posY = radio - (int) (angulos[1] / 10 * radio) + (dimenh / 4);

        canvas.drawBitmap(burbuja, posX, posY, null);
    }

    public void angulos(float[] angulos) {
        this.angulos = angulos;
        invalidate();
    }

}

