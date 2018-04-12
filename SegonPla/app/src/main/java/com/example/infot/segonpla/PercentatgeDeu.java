package com.example.infot.segonpla;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by infot on 01/03/18.
 */

public class PercentatgeDeu extends AsyncTask <Integer, Integer, String>{

    //--------------VRIABLES----------------

    private int tiempo = 1000;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar progressBar1;
    @SuppressLint("StaticFieldLeak")
    private TextView textView;

    PercentatgeDeu(ProgressBar progressBar1, TextView textView) {
        this.progressBar1 = progressBar1;
        this.textView = textView;
    }

    //Realiza en segundo plano ....
    @Override
    protected String doInBackground(Integer... integers) {

        for (int i = 0; i < 100; i++) {

            try {
                //Lanza el metodo onProgressUpdate y le pasamos el parametro i
                //Para actualizar la barra y el textView
                Thread.sleep(tiempo);
                publishProgress(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "Task Completed.";

    }

    //Se ejucuta con el metodo publishProgress()
    //Actualiza la barra de progreso y el textView
    @SuppressLint("SetTextI18n")
    @Override
    protected void onProgressUpdate(Integer... values) {

        //Le pasamos el parametro, es un array y esta en la posicion 0
        progressBar1.setProgress(values[0]);
        //Le sumamos 1 pork y 1mpieza en 0
        textView.setText(values[0]+1 + " %");
    }


}
