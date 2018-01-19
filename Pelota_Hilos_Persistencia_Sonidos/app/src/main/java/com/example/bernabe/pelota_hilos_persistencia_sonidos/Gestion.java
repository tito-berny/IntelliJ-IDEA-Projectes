package com.example.bernabe.pelota_hilos_persistencia_sonidos;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;



/**
 * Created by bernabe on 19/1/18.
 */

public class Gestion extends AyudaActivity{

    //----------- VARIABLES------------------------------
    //Clase partida
    private Partida partida;
    //dificultad 1 2 3 segun facil estandas o dificil
    private int dificultad;
    //Flame por segundo, refresco de pantalla, movimiento de la pelota
    private int FPS=30;
    //Cuanto tarda en comenzar la animacion, Handler es el controlador de un hilo
    private Handler temporizador;


    //----------------PROGRAMA ----------------------------

    /**
     * Sobreescribimos onCreate para poder guardar y pasar parametros entre clases
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Rescatamos el nivel de dificultat desde la otra activiti (MainActivity)
        Bundle extras = getIntent().getExtras();

        dificultad = extras.getInt("DIFICULTAD");

        //Hacemos que empiece la partida, le pasamos parametros ( contexto y dificultatd Int)
        partida = new Partida(getApplicationContext(), dificultad);
        //Carga la partida
        setContentView(partida);

        //Instanciamos el nuevo HAndler
        temporizador= new Handler();

        //Se ejecuta el hilo con 2000 milisegundos, la pelota tardara en caer ese tiempo
        temporizador.postDelayed(elhilo, 2000);
    }

    /**
     * Crea el hilo donde controla el movimiento de la pelota
     */
    private Runnable elhilo = new Runnable() {
        @Override
        //Metodo run, todos los hilos deben llevarla, es donde se ejecutara el hilo
        public void run() {

            if (partida.movimientoBola() == true) {
                fin();

            } else {

                partida.invalidate();  //Elimina el contenido de imageView y llama de nuevo a onDraw()

                temporizador.postDelayed(elhilo, 1000 / FPS);

            }
        }
    };

    /**
     * Cuando el usuari toque la pantalla
     * @param evento
     * @return false
     */
    public boolean onTouchEvent (MotionEvent evento) {

        //Capturamos las cordenadas donde toca el usuario
        int x = (int) evento.getX();

        int y = (int) evento.getY();

        //Pasa los parametros a la funcion que se encarga de toque de la pelota
        partida.toque(x,y);

        return false;


    }

    /**
     * Termina el hilo y elimina la actividad
     */
    public void fin(){

        //Limpia el hilo
        temporizador.removeCallbacks(elhilo);

        //Destruye actividad actual
        finish();
    }
}
