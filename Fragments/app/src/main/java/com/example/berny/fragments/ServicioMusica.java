package com.example.berny.fragments;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class ServicioMusica extends Service {

    MediaPlayer miReproductor;

    public void onCreate (){
        super.onCreate();

        //Seleccionamos el archivo de audio
        miReproductor=MediaPlayer.create(this, R.raw.musica);

        //Con este comando hacemos que la musica se reproduzca en bucle
        miReproductor.setLooping(true);

        //Seleccionamos el volumen de la musica
        miReproductor.setVolume(100,100);


    }

        public int onStartCommand(Intent intent, int flags, int startId ){


        miReproductor.start();

        return START_STICKY;
    }

    public void onDestroy (){

        super.onDestroy();

        //Si la musica esta sonando la paramos
        if (miReproductor.isPlaying()) miReproductor.stop();

        //Liberamos recursos en la memosria
        miReproductor.release();

        miReproductor=null;


    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
