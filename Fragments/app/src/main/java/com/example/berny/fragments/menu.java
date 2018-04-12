package com.example.berny.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu extends Fragment {

    //Array para los botones
    private final int[] BOTONESMENU = {R.id.linterna, R.id.musica, R.id.nivel, R.id.descarga};
    //Arrray para los botones iluminados
    private final int[] BOTONESILUMINADOS = {R.drawable.linterna2, R.drawable.musica2, R.drawable.nivel2, R.drawable.descarga2};
    //Variable para recibir el boton pulsado desde la acctividad
    private int boton;

    public Menu() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mimenu = inflater.inflate(R.layout.fragment_menu, container, false);

        boton = -1;


        //Controla si se carga por primera vez el menu no de error al no recibir ningun argumento de ActividadHerramienta
        if (getArguments() != null) {
            //Rescatamos la informacion del badle mandado del ActividadHerramientas para saber que boton se a pulsado
            boton = getArguments().getInt("BOTONPULSADO");
        }

        ImageButton botonmenu;

        for (int i = 0 ; i<BOTONESMENU.length; i++){

            //Hacemos un casting de las imagenes en los botones
            botonmenu = (ImageButton) mimenu.findViewById(BOTONESMENU[i]);

            //Cambia la imagen de fondo de los botones, si coincide con el fragment elegido se
            //cambia el boton a iluminado
            if (boton == i){
                botonmenu.setImageResource(BOTONESILUMINADOS[i]);
            }

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
