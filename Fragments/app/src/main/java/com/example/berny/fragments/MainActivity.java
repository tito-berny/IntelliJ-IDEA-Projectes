package com.example.berny.fragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ComunicaMenu{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void menu(int queboton) {

        //Creamos el intent
        Intent in = new Intent(this, ActividadHerramientas.class);

        //Cargamos el bundle con la informacion
        in.putExtra("botonpulsado", queboton);

        startActivity(in);

    }
}
