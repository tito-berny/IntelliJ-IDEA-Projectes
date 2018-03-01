package com.example.infot.segonpla;

import android.os.AsyncTask;
import android.widget.ProgressBar;

/**
 * Created by infot on 01/03/18.
 */

public class PercentatgeDos extends AsyncTask <Integer, Integer, String> {

    private int tiempo = 200;
    private ProgressBar progressBar1;


    public PercentatgeDos(ProgressBar progressBar1) {
        this.progressBar1 = progressBar1;
    }

    @Override
    protected String doInBackground(Integer... integers) {

        for (int i = 0; i < 100; i++) {

            try {

                Thread.sleep(tiempo);
                publishProgress(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "Task Completed.";

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        progressBar1.setProgress(values[0]);
    }
}
