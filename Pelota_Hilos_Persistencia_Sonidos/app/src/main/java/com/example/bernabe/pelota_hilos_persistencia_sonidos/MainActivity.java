package com.example.bernabe.pelota_hilos_persistencia_sonidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ayuda(View view){

        Intent intencion =new Intent(this, AyudaActivity.class);

        startActivity(intencion);
    }

    /**
     * Asigna la dificultad del juego segun el boton pulsado por el usuario
     * @param vista
     */
    public void dificultad(View vista) {

        //Recoge ek texto del boton pulsado por el usuario
        String dific = (String) ((Button) vista).getText();

        int dificultad = 1;

        //Si pulsa sobre el boton Standard pone la dificultad en 2
        if (dific.equals("Standard")) dificultad=2;
        //Si pulsa sobre el boton Dificult pone la dificultad en 3
        if (dific.equals("Dificult")) dificultad=3;

        //PAsamos los datos de la dificultad
        Intent in = new Intent(this, Gestion.class);

        in.putExtra("DIFICULTAD", dificultad);

        //Empezamos el juego
        startActivity(in);


    }
}
