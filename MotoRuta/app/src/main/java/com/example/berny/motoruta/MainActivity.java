package com.example.berny.motoruta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button buscarRuta, crearRuta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buscarRuta = (Button) findViewById(R.id.bBuscarRuta);
        crearRuta = (Button) findViewById(R.id.bCrearRuta);

        //Escuchador de clicks en botones lanza vista buscar ruta
        buscarRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent buscar = new Intent(MainActivity.this, MapsActivity.class);

                startActivity(buscar);

            }
        });

        //Escuchador de clicks en botones, pone 0 todos los valores
        crearRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent crear = new Intent(MainActivity.this, SplashScreenActivity.class);

                startActivity(crear);

            }
        });

    }
}
