package com.example.berny.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Musica extends Fragment {

    public boolean  encendida;


    private ImageView botonMusica;


    public Musica() {
        // Required empty public constructor
    }


    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        encendida=false;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento =  inflater.inflate(R.layout.fragment_musica, container, false);


        botonMusica = (ImageView) fragmento.findViewById(R.id.musica);

        if(encendida) botonMusica.setImageResource(R.drawable.musica2);

        //getActivity().getRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        botonMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (encendida) apagaMusica();
                else enciendeMusica();

            }

        });

        return fragmento;

    }


    private void enciendeMusica() {

        //boton para encender o apagar musica
        botonMusica.setImageResource(R.drawable.musica2);

            Intent miReproductor = new Intent(getActivity(), ServicioMusica.class);

            //Suficiente para que empiece a sonar la musica
            getActivity().startService(miReproductor);

            encendida = !encendida;
    }


    private void apagaMusica() {

        //boton para encender o apagar musica
        botonMusica.setImageResource(R.drawable.musica);

        Intent miReproductor = new Intent(getActivity(), ServicioMusica.class);

        //Suficiente para que empiece a sonar la musica
        getActivity().stopService(miReproductor);

        encendida = !encendida;

    }
}
