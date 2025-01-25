package com.example.ikergomezrubio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Contactos extends AppCompatActivity {
    private Button botonInsertar, botonBuscar, botonActualizar, botonBorrar;
    private EditText textoNombre, textoApellidos, textoTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contactos);
        botonInsertar = findViewById(R.id.binsertar);
        botonBuscar = findViewById(R.id.bbuscar);
        botonActualizar = findViewById(R.id.bactualizar);
        botonBorrar = findViewById(R.id.bborrar);
        textoNombre = findViewById(R.id.editNombre);
        textoApellidos = findViewById(R.id.editApellidos);
        textoTelefono = findViewById(R.id.editTelefono);
        BBDD_Acciones accion = new BBDD_Acciones(this);

        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = accion.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.NOMBRE_COLUMNA1, textoNombre.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2, textoApellidos.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA3, textoTelefono.getText().toString());

                long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);
                Toast.makeText(getApplicationContext(), "Se insertaron los datos: " + newRowId, Toast.LENGTH_LONG).show();
                db.close();
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = accion.getReadableDatabase();
                String[] projection = {
                        Estructura_BBDD.NOMBRE_COLUMNA1, // Nombre
                        Estructura_BBDD.NOMBRE_COLUMNA2, // Apellidos
                };

                String selection = Estructura_BBDD.NOMBRE_COLUMNA3 + " = ?";
                String[] selectionArgs = {textoTelefono.getText().toString()};
                Cursor cursor = db.query(
                        Estructura_BBDD.TABLE_NAME,   // Tabla
                        projection,                   // Columnas a devolver
                        selection,                    // Cláusula WHERE
                        selectionArgs,                // Valores para la cláusula WHERE
                        null,                         // No agrupar las filas
                        null,                         // No filtrar por grupo
                        null                          // Orden
                );

                if (cursor.moveToFirst()) {
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow(Estructura_BBDD.NOMBRE_COLUMNA1));
                    String apellidos = cursor.getString(cursor.getColumnIndexOrThrow(Estructura_BBDD.NOMBRE_COLUMNA2));
                    textoNombre.setText(nombre);
                    textoApellidos.setText(apellidos);
                    Toast.makeText(getApplicationContext(), "Contacto encontrado", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Contacto no encontrado", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
                db.close();
            }
        });


    }
}


