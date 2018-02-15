package com.example.infot.barra_progreso_android;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends Activity {

    Handler handler = new Controlador();

    ProgressBar pro;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pro = (ProgressBar) findViewById(R.id.progressBar);
        b = (Button)findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Hilo hilo1 = new Hilo(10000, 1);

                hilo1.start();
            }
        });
    }



    class Hilo extends Thread {

        int maximo, tiempo;

        Hilo(int n, int t) {

            maximo = n;
            tiempo = t;
        }

        @Override

        public void run() {

            for (int i = 0; i < maximo; i++) {
                try {
                    Thread.sleep(tiempo);
                } catch (InterruptedException e) {
                    ;
                }


                Message msg = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("total", i);
                msg.setData(b);
                handler.sendMessage(msg);
            }
        }
    }


    public class Controlador extends android.os.Handler {


        @Override
        public void handleMessage(Message msg) {

            int total;

            total = msg.getData().getInt("total");
            pro.setProgress(total);
        }
    }

}
