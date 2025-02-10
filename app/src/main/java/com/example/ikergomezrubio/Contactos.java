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
    Button botonInsertar, botonBuscar, botonActualizar, botonBorrar;
    EditText textoNombre, textoApellidos, textoTelefono;

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
            }
        });

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener una instancia de la base de datos en modo escritura
                SQLiteDatabase db = accion.getWritableDatabase();

                // Crear un objeto ContentValues para almacenar los nuevos valores
                ContentValues valores = new ContentValues();

                // Obtener los valores de los campos (asegúrate de que estos EditText existan en tu diseño)
                String nuevoNombre = textoNombre.getText().toString();
                String nuevoApellido = textoApellidos.getText().toString();
                String nuevoTelefono = textoTelefono.getText().toString();

                // Agregar los valores al objeto ContentValues
                valores.put("nombre", nuevoNombre);
                valores.put("apellido", nuevoApellido);
                valores.put("telefono", nuevoTelefono);

                // Usar el valor de textoTelefono para la cláusula WHERE correctamente
                String whereClause = "telefono = ?";
                String[] whereArgs = new String[]{nuevoTelefono};

                int filasActualizadas = db.update("tu_tabla", valores, whereClause, whereArgs);

                // Verificar si se actualizó correctamente
                if (filasActualizadas > 0) {
                    Toast.makeText(v.getContext(), "Actualización completada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "No se pudo actualizar", Toast.LENGTH_SHORT).show();
                }
                // Cerrar la base de datos
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

        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener una instancia de la base de datos en modo escritura
                SQLiteDatabase db = accion.getWritableDatabase();

                // Obtener el valor del teléfono ingresado por el usuario
                String telefono = textoTelefono.getText().toString();

                // Verificar que el campo de teléfono no esté vacío
                if (telefono.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese un número de teléfono", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Definir la cláusula WHERE para eliminar el registro correspondiente
                String whereClause = "telefono = ?";
                String[] whereArgs = {telefono};

                // Ejecutar la operación de eliminación
                int filasEliminadas = db.delete(Estructura_BBDD.TABLE_NAME, whereClause, whereArgs);

                // Verificar si se eliminó correctamente
                if (filasEliminadas > 0) {
                    Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_SHORT).show();
                    // Limpiar los campos de texto
                    textoNombre.setText("");
                    textoApellidos.setText("");
                    textoTelefono.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "No se encontró el contacto", Toast.LENGTH_SHORT).show();
                }

                // Cerrar la base de datos
                db.close();
            }
        });


    }
}


