package com.example.berny.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ActividadHerramientas extends AppCompatActivity implements ComunicaMenu, ManejaFlashCamara {

    //VAriable objeto manejador de camara
    private CameraManager miCamara;
    //ID identificativo de la camara
    private String idCamara;
    //Array de Fragments para los botones
    Fragment[] misFragmentos ;

    @Override
    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_herramientas);

        //Inicializamos el Array
        misFragmentos =new Fragment[4];

        //Asignamos un fragment a cada posicion
        misFragmentos[0] = new Linterna();
        misFragmentos[1] = new Musica();
        misFragmentos[2] = new Nivel();
        misFragmentos[3] = new Descarga();

        Bundle extras = getIntent().getExtras();

        menu(extras.getInt("BOTONPULSADO"));

        //Creamos un nuevo objeto del manejador de la camara
         miCamara  = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

         //Optenemos una lista de todas las camaras del dispositivo en una Array
        try {
            idCamara = miCamara.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void menu(int queboton) {

        //Creamos un manejador
        FragmentManager miManejador = getFragmentManager();
        //Para que comience la transaccion
        FragmentTransaction miTransaccion = miManejador.beginTransaction();

        //Creamos un fragmento nuevo de menu
        Fragment menu_iluminado = new Menu();

        //Creamos un bundle para pasar al menu el boton pulsado
        Bundle datos = new Bundle();
        datos.putInt("BOTONPULSADO", queboton);

        //Le pasamos al fragment los datos
        menu_iluminado.setArguments(datos);

        miTransaccion.replace(R.id.menu, menu_iluminado );

        //Reemplazamos los Fragments
        miTransaccion.replace(R.id.herramientas, misFragmentos[queboton]);

        miTransaccion.commit();

    }

    @Override
    public void enciendeApaga(boolean estadoFlash) {

        try {


            if (estadoFlash) {
                Toast.makeText(this, "Flash apagado", Toast.LENGTH_SHORT).show();

                //Controlamos que la version de Android sea superior a la de MarsMalow con M
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //ENCENDEMOS LA CAMARA
                    miCamara.setTorchMode(idCamara, false);
                }

            } else {
                Toast.makeText(this, "Flash activado", Toast.LENGTH_SHORT).show();

                //Controlamos que la version de Android sea superior a la de MarsMalow con M
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //ENCENDEMOS LA CAMARA
                    miCamara.setTorchMode(idCamara, true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Sale de la aplicacion con el boton de atras
    public void onBackPressed(){

        moveTaskToBack(true);
    }
}
