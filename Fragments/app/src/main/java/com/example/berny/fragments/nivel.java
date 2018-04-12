package com.example.berny.fragments;


import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Nivel extends Fragment implements SensorEventListener{

    //VAriable Manejador de los sensores
    private SensorManager miManager;

    //El sensor seleccionado
    private Sensor miSensor;

    private NivelPantalla pantalla;

    public Nivel() {
        // Required empty public constructor
    }

    public final void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);

        //Crea un objeto de la clase sensorManager y le pasamos la Activity
        miManager= (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        //Accede al acelerometro del dispositivo
        miSensor = miManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Recivimos es tamañamo del lado, como depende la la pantalla del dispositivo
        //la recojemos utilizando getDimensionPixelSice y como maximo le ponemos
        //el valor que yaa emos definido en dimen
        int lado = getResources().getDimensionPixelSize(R.dimen.maximo);

        pantalla = new NivelPantalla(getActivity(), lado);




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_nivel, container, false);

        return pantalla;
    }

    public void onResume() {

        super.onResume();

        //Ponemos el sensor a la escucha
        miManager.registerListener(this, miSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onPause() {

        super.onPause();

        //Poemos el lisener en pause para que deje de recivir los latos del sensor
        miManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        pantalla.angulos(event.values);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
