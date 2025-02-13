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
    BBDD_Acciones accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contactos);

        // Inicializar vistas
        botonInsertar = findViewById(R.id.binsertar);
        botonBuscar = findViewById(R.id.bbuscar);
        botonActualizar = findViewById(R.id.bactualizar);
        botonBorrar = findViewById(R.id.bborrar);
        textoNombre = findViewById(R.id.editNombre);
        textoApellidos = findViewById(R.id.editApellidos);
        textoTelefono = findViewById(R.id.editTelefono);

        // Inicializar la base de datos
        accion = new BBDD_Acciones(this);

        // Insertar registro
        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = accion.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.NOMBRE_COLUMNA1, textoNombre.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2, textoApellidos.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA3, textoTelefono.getText().toString());

                try {
                    long newRowId = db.insertOrThrow(Estructura_BBDD.TABLE_NAME, null, values);
                    Toast.makeText(getApplicationContext(), "Se insertaron los datos: " + newRowId, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: El teléfono ya existe y no se puede repetir.", Toast.LENGTH_LONG).show();
                } finally {
                    db.close();
                }
            }
        });

        // Actualizar registro
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarRegistro(v);
            }
        });

        // Buscar registro
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = accion.getReadableDatabase();
                String[] projection = {
                        Estructura_BBDD.NOMBRE_COLUMNA1, // Nombre
                        Estructura_BBDD.NOMBRE_COLUMNA2  // Apellidos
                };

                String selection = Estructura_BBDD.NOMBRE_COLUMNA3 + " = ?";
                String[] selectionArgs = {textoTelefono.getText().toString()};
                Cursor cursor = db.query(
                        Estructura_BBDD.TABLE_NAME, // Tabla
                        projection,                // Columnas a devolver
                        selection,                 // Cláusula WHERE
                        selectionArgs,             // Valores para la cláusula WHERE
                        null,                      // No agrupar las filas
                        null,                      // No filtrar por grupo
                        null                       // Orden
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

        // Borrar registro
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = accion.getWritableDatabase();
                String telefono = textoTelefono.getText().toString();

                if (telefono.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese un número de teléfono", Toast.LENGTH_SHORT).show();
                    return;
                }

                String whereClause = Estructura_BBDD.NOMBRE_COLUMNA3 + " = ?";
                String[] whereArgs = {telefono};

                int filasEliminadas = db.delete(Estructura_BBDD.TABLE_NAME, whereClause, whereArgs);

                if (filasEliminadas > 0) {
                    Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_SHORT).show();
                    textoNombre.setText("");
                    textoApellidos.setText("");
                    textoTelefono.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "No se encontró el contacto", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });
    }

    // Método para actualizar el registro
    private void actualizarRegistro(View v) {
        // Obtener y limpiar los valores de los EditText
        String nuevoNombre = textoNombre.getText().toString().trim();
        String nuevoApellido = textoApellidos.getText().toString().trim();
        String nuevoTelefono = textoTelefono.getText().toString().trim();

        // Validar que ningún campo esté vacío
        if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevoTelefono.isEmpty()) {
            Toast.makeText(v.getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = null;
        try {
            if (accion == null) {
                Toast.makeText(v.getContext(), "Error: Base de datos no inicializada", Toast.LENGTH_SHORT).show();
                return;
            }
            db = accion.getWritableDatabase();

            // Preparar los valores a actualizar
            ContentValues valores = new ContentValues();
            valores.put(Estructura_BBDD.NOMBRE_COLUMNA1, nuevoNombre);
            valores.put(Estructura_BBDD.NOMBRE_COLUMNA2, nuevoApellido);
            valores.put(Estructura_BBDD.NOMBRE_COLUMNA3, nuevoTelefono);

            // Establecer la condición de actualización: actualizar el registro cuyo teléfono coincida
            String whereClause = Estructura_BBDD.NOMBRE_COLUMNA3 + " = ?";
            String[] whereArgs = {nuevoTelefono};

            int filasActualizadas = db.update(Estructura_BBDD.TABLE_NAME, valores, whereClause, whereArgs);

            if (filasActualizadas > 0) {
                Toast.makeText(v.getContext(), "Actualización completada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "No se encontró el registro para actualizar", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Error al actualizar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
}
