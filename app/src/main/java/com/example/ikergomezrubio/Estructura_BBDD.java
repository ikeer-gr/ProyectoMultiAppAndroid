package com.example.ikergomezrubio;

public class Estructura_BBDD {

    private Estructura_BBDD() {
    }

    public static final String TABLE_NAME = "Clientes";
    public static final String NOMBRE_COLUMNA1 = "nombre";
    public static final String NOMBRE_COLUMNA2 = "apellidos";
    public static final String NOMBRE_COLUMNA3 = "telefono";
    public static final String _ID = "id";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME + " (" +
                    Estructura_BBDD._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    Estructura_BBDD.NOMBRE_COLUMNA1 + TEXT_TYPE + COMMA_SEP +
                    Estructura_BBDD.NOMBRE_COLUMNA2 + TEXT_TYPE + COMMA_SEP +
                    Estructura_BBDD.NOMBRE_COLUMNA3 + " TEXT UNIQUE);";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME;

}
