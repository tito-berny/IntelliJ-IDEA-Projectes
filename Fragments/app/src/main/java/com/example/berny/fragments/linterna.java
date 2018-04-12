package com.example.berny.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Linterna extends Fragment {

    //Variable de boton
    private ImageView botonCamara ;
    //Variable encapsuladad para el boton de linterna on/off
    private boolean encendida;


    public Linterna() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento =  inflater.inflate(R.layout.fragment_linterna, container, false);

        botonCamara = (ImageView) fragmento.findViewById(R.id.linterna);

        //Controlamos que i eta ya encendido el flas y volvemos a la linterna la imagen sea la de encendida
        if(encendida) botonCamara.setImageResource(R.drawable.linterna2);


        botonCamara.setOnClickListener(new View.OnClickListener() {
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


    private void enciendeFlash() {

        botonCamara.setImageResource(R.drawable.linterna2);

        //Guadamos en la variable la actividad donde estamos
        Activity estaActividad = getActivity();

        //Hacemos un casting de estaActividad y la convertimos en ManejaFlashCamara
        ((ManejaFlashCamara)estaActividad).enciendeApaga(encendida);
    }

    private void botonApagaFlash() {

        botonCamara.setImageResource(R.drawable.linterna);

        //Guadamos en la variable la actividad donde estamos
        Activity estaActividad = getActivity();

        //Hacemos un casting de estaActividad y la convertimos en ManejaFlashCamara
        ((ManejaFlashCamara)estaActividad).enciendeApaga(encendida);
    }
}
