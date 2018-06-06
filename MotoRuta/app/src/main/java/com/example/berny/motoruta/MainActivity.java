package com.example.berny.motoruta;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.berny.motoruta.DataBase.ConexionSQLiteHelper;
import com.example.berny.motoruta.Utilidades.Utilidades;

public class MainActivity extends Activity {

    public ConexionSQLiteHelper con;

    Button buscarRuta, crearRuta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------------------------BASE DATOS -------------------------------------------------------

        //INICIAMOS LA CONECSION LA BBDD Creara las tablas si es la 1 vez que se ejecuta
        con = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null, Utilidades.DATABASE_VERSION);


        //**********PARA FORENG KEY FUNCIONEN****************
        /*SQLiteDatabase db = pSQL.getWritableDatabase();
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }*/

        crearUsuarioSQL();




        //-----------------     BOTONES            -------------------------------------------------

        buscarRuta = (Button) findViewById(R.id.bBuscarRuta);
        crearRuta = (Button) findViewById(R.id.bCrearRuta);


        //Escuchador de clicks en botones lanza vista buscar ruta
        buscarRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent buscar = new Intent(MainActivity.this, BuscarRutaActivity.class);

                //buscar.putExtra("conexion", (Parcelable) con);

                startActivity(buscar);

            }
        });

        //Escuchador de clicks en botones, pone 0 todos los valores
        crearRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent crear = new Intent(MainActivity.this, CrearRutaActivity.class);

                startActivity(crear);

            }
        });

        //..........................................................................................
    }


    private void crearUsuarioSQL() {

        SQLiteDatabase db = con.getWritableDatabase();

        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }

        String alias="guay";
        String email="manolo@gtr.es";
        String pass="passeo";
        String moto="yamaha R1";
        int nivel=2;

        //Insert into usuario
        String insert = "INSERT INTO tbl_usuario (alias, email, pass, moto, nivel) VALUES ('"+alias+"','"+email+"','"+pass+"','"+moto+ "',"+ nivel+");";

        db.execSQL(insert);

        //Cerramos conecsion
        db.close();
    }
}
