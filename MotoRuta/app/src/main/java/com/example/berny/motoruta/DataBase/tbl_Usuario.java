package com.example.berny.motoruta.DataBase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class tbl_Usuario extends SQLiteOpenHelper{

    //variables estáticas para poder administrar de mejor manera nuestra base de datos
    //nuemero de version
    public static final int DATABASE_VERSION = 1;
    //Nombre de la base de datos
    public static final String DATABASE_NAME = "motoRuta.db";

    //NOMBRE TABLA
    public static final String TABLA_USUARIO = "tbl_usuario";
    //NOMBRE COLUMNAS
    public static final String COLUMNA_ID = "id";
    public static final String COLUMNA_ALIAS = "alias";
    public static final String COLUMNA_EMAIL = "email";
    public static final String COLUMNA_PASS = "pass";
    public static final String COLUMNA_MOTO = "moto";
    public static final String COLUMNA_NIVEL = "nivel";

    //String sentencia creacion de tabla
    private static final String SQL_CREAR = "create table " + TABLA_USUARIO
            + "(" + COLUMNA_ID + " integer primary key autoincrement, "
                  + COLUMNA_ALIAS + " text not null,"
                  + COLUMNA_EMAIL + " text not null,"
                  + COLUMNA_PASS + " text not null,"
                  + COLUMNA_MOTO + " text,"
                  + COLUMNA_NIVEL + " integer"
            + ");";



    public tbl_Usuario(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public tbl_Usuario(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public tbl_Usuario(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Crea la tabla
        db.execSQL(SQL_CREAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Si ya existe la tabl la borra
        db.execSQL("DROP TABLE IF EXISTS usuario;");
        //Crea de nuevo la tabla usuario
        onCreate(db);
    }
}
