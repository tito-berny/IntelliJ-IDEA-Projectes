package com.example.berny.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Descarga extends Fragment {

    //Variable de boton
    private ImageView descarga ;
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

        descarga = (ImageView) fragmento.findViewById(R.id.descarga);

        descarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (encendida){
                    botonApagaFlash();

                    //Apagamos el flash de la camara
                    encendida = false;



                }else{
                    enciendeFlash();
                    //Encendemos el flash de la camara
                    encendida = true;
                }
            }
        });

        return fragmento;
    }

}
