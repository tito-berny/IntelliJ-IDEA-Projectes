package com.example.berny.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu extends Fragment {

    private final int[] BOTONESMENU = {R.id.linterna, R.id.musica, R.id.nivel};

    public Menu() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mimenu = inflater.inflate(R.layout.fragment_menu, container, false);


        ImageButton botonmenu;

        for (int i = 0 ; i<BOTONESMENU.length; i++){

            //Hacemos un casting de las imagenes en los botones
            botonmenu = (ImageButton) mimenu.findViewById(BOTONESMENU[i]);

            final int queBoton = i;

            botonmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Guadamos en la variable la actividad donde estamos
                    Activity estaActividad = getActivity();

                    //Hacemos un casting de estaActividad y la convertimos en ComunicaMenu
                    ((ComunicaMenu)estaActividad).menu(queBoton);

                }
            });
        }


        return mimenu;
    }

}
