package com.example.berny.motoruta;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.berny.motoruta.DataBase.ConexionSQLiteHelper;
import com.example.berny.motoruta.Utilidades.Utilidades;

public class MainActivity extends Activity {

    public ConexionSQLiteHelper con;

    private Button buscarRuta, crearRuta;

    //Controla si hay alguna ruta guardada en la tabla tbl_ruta de la BBDD
    boolean alguna_ruta_guardada;

    //TOdo audio
    //private MediaPlayer mpInicia1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recibe datos de Splash (si hay alguna ruta guardada
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            //tfTiempo.setText(tiempo);
            alguna_ruta_guardada = parametros.getBoolean("ruta_guardada");

        }

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
        //TODO sonido
        //mpInicia1 = MediaPlayer.create(this, R.raw.cbrarranca);


        //Escuchador de clicks en botones lanza vista buscar ruta
        buscarRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (alguna_ruta_guardada) {

                    //Lanzamos BuscarRutaActivity
                    Intent buscar = new Intent(MainActivity.this, BuscarRutaActivity.class);
                    //Iniciamos actividad
                    startActivity(buscar);

                    //TODO sonido
                    //mpInicia1.start();

                 //Avisamos al usuario que cree una nueva ruta ya que no hay ninguna guardada para mostrar
                }else {

                    Toast toast = Toast.makeText(getBaseContext(), R.string.Toast_NO_iniciado, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
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
