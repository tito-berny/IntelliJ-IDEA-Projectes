package com.example.berny.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Descarga extends Fragment {

    public static final String url = "http://www.thebiblescholar.com/android_awesome.jpg";
    private ImageView imgImagen;

    //Variable encapsuladad para el boton de linterna on/off
    private boolean encendida;


    public Descarga() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragment_descarga, container, false);

        imgImagen = (ImageView) fragmento.findViewById(R.id.descarga);

        imgImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!encendida){

                    //Llama al metodo que inicia la descarga
                    CargaImagenes nuevaTarea = new CargaImagenes();
                    nuevaTarea.execute(url);

                    encendida = false;



                }else{

                    encendida = true;
                }
            }
        });

        return fragmento;
    }

    public class CargaImagenes extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground", "Entra en doInBackground");
            String url = params[0];
            Bitmap imagen = descargarImagen(url);
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            imgImagen.setImageBitmap(result);
        }


        private Bitmap descargarImagen(String imageHttpAddress) {
            java.net.URL imageUrl = null;
            Bitmap imagen = null;
            try {
                imageUrl = new URL(imageHttpAddress);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagen = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return imagen;
        }
    }

}
