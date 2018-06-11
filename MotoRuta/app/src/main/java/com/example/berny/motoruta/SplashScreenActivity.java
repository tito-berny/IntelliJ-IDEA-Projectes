package com.example.berny.motoruta;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;

import com.example.berny.motoruta.DataBase.ConexionSQLiteHelper;
import com.example.berny.motoruta.Utilidades.Utilidades;

public class SplashScreenActivity extends Activity {

    // Seleccionamos la durcion de la pantalla de Splash
    private static final long SPLASH_SCREEN_DELAY = 4000;

    //Conexion con la BBDD
    public ConexionSQLiteHelper con;

    //Variable para comprovar si existe alguna ruta guardada
    //Si no en BuscarRuta al no encontrar ninguna explotaria
    private boolean ruta_guardada=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seleccionamos portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Quitamos la barra de arriba con el titulo de la APP
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_screen);

        //INICIAMOS LA CONEXION LA BBDD Creara las tablas si es la 1 vez que se ejecuta
        con = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null, Utilidades.DATABASE_VERSION);

        //Iniciamos una conexion para lectura
        SQLiteDatabase db = con.getReadableDatabase();

        //Creamos cursor con la consulta BBDD
        Cursor c = db.rawQuery("SELECT * FROM tbl_ruta;", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            ruta_guardada=true;
        }else {
            ruta_guardada=false;
        }

        db.close();


        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Llamamos a la nueva activity MainActivity y pasamos boolean ruta_guardada
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                mainIntent.putExtra("ruta_guardada",ruta_guardada);
                startActivity(mainIntent);

                // Cerramos la Activity
                // activity pressing Back button
                finish();
            }
        };

        // Simulamos un largo proceso de carga al inicio de la aplicación.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

}
