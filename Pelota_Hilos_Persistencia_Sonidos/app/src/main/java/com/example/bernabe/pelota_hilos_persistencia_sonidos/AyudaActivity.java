package com.example.bernabe.pelota_hilos_persistencia_sonidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AyudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
    }

    /**
     * Imita al boton atras del terminal
     * @param view Recive una vista
     */
    public void volver (View view){

        onBackPressed();
    }
}
