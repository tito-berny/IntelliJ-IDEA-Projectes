package com.example.infot.segonpla;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    //------------VARIABLES-------------

        ProgressBar uno, dos, tres;
        TextView textView1, textView2, textView3;
        Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignamos los textView
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        //Asignamos los ProgressBar
        uno = (ProgressBar) findViewById(R.id.progressBar1);
        dos = (ProgressBar) findViewById(R.id.progressBar2);
        tres = (ProgressBar) findViewById(R.id.progressBar3);

        //Asignamos el Button
        b = (Button)findViewById(R.id.button);

        //Escuchador del boton
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Iniciamos las clases, le pasamos al constructor la progressBar y el textView respectivos
                //executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR) indicamos que se ejecuten todos
                //los hilos al mismo tiempo, si no se ejecuta primero 1, despue  el 2 y por ultimo 3
                new PercentatgeDos(uno, textView1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new PorcentatgeCinc(dos, textView2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new PercentatgeDeu(tres, textView3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        });

    }
}
