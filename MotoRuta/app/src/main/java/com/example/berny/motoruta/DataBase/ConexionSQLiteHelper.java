package com.example.berny.motoruta.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.berny.motoruta.Utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidades.SQL_CREAR_TBL_USUARIO);
        db.execSQL(Utilidades.SQL_CREAR_TBL_RUTA);
        db.execSQL(Utilidades.SQL_CREAR_TBL_COMENTARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_RUTA);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_COMENTARIO);

        onCreate(db);

    }
}
