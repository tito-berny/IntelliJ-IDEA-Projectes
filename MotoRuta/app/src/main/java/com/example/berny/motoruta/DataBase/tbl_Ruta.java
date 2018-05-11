package com.example.berny.motoruta.DataBase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class tbl_Ruta extends SQLiteOpenHelper {

    //nuemero de version
    public static final int DATABASE_VERSION = 1;
    //Nombre de la base de datos
    public static final String DATABASE_NAME = "motoRuta.db";

    //NOMBRE TABLA
    public static final String TABLA_RUTA = "tbl_ruta";
    //NOMBRE COLUMNAS
    public static final String COLUMNA_ID = "id";
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_FECHA = "fecha";
    public static final String COLUMNA_TIEMPO = "tiempo";
    public static final String COLUMNA_METEOROLOGIA = "meteorología";
    public static final String COLUMNA_VALORACION = "valoracion";
    public static final String COLUMNA_PATH = "path";


    //String sentencia creacion de tabla
    private static final String SQL_CREAR = "create table " + TABLA_RUTA
            + "(" + COLUMNA_ID + " integer primary key autoincrement, "
                  + COLUMNA_NOMBRE + " text not null,"
                  + COLUMNA_FECHA + " text,"
                  + COLUMNA_TIEMPO + " integer,"
                  + COLUMNA_METEOROLOGIA + " integer,"
                  + COLUMNA_VALORACION + " integer,"
                  + COLUMNA_PATH + " text"
            + ");";

    public tbl_Ruta(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public tbl_Ruta(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public tbl_Ruta(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
