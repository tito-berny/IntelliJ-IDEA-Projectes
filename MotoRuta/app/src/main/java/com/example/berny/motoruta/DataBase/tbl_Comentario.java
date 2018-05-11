package com.example.berny.motoruta.DataBase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.berny.motoruta.DataBase.tbl_Ruta.TABLA_RUTA;
import static com.example.berny.motoruta.Utilidades.Utilidades.COLUMNA_ID_RUTA;

public class tbl_Comentario extends SQLiteOpenHelper {

    //nuemero de version
    public static final int DATABASE_VERSION = 1;
    //Nombre de la base de datos
    public static final String DATABASE_NAME = "motoRuta.db";

    //NOMBRE TABLA
    public static final String TABLA_COMENTARIO = "tbl_comentario";
    //NOMBRE COLUMNAS
    public static final String COLUMNA_ID_COMENTARIO = "id";
    public static final String COLUMNA_RUTA_ID = "ruta_id";
    public static final String COLUMNA_COMENTARIO = "comentario";



    //String sentencia creacion de tabla
    private static final String SQL_CREAR_TBL_COMENTARIO = "create table " + TABLA_COMENTARIO
            + "(" + COLUMNA_ID_COMENTARIO + " integer primary key autoincrement,"
            + COLUMNA_RUTA_ID + " integer,"
            + COLUMNA_COMENTARIO + " text,"
            + "FOREIGN KEY (" + COLUMNA_RUTA_ID + ") REFERENCES " + TABLA_RUTA + " (" + COLUMNA_ID_RUTA + ")"
            + " );";


    public tbl_Comentario(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public tbl_Comentario(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public tbl_Comentario(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREAR_TBL_COMENTARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
