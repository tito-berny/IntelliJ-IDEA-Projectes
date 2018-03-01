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

        ProgressBar uno, dos, tres;
        TextView textView1;
        Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.textView);

        uno = (ProgressBar) findViewById(R.id.progressBar1);
        dos = (ProgressBar) findViewById(R.id.progressBar2);
        tres = (ProgressBar) findViewById(R.id.progressBar3);

        b = (Button)findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView1.setText("Hola");
                new PercentatgeDos(uno).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new PorcentatgeCinc(dos).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new PercentatgeDeu(tres).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        });

    }
}
