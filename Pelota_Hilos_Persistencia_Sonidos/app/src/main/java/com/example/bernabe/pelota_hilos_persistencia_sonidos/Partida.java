package com.example.bernabe.pelota_hilos_persistencia_sonidos;

/**
 * Created by bernabe on 18/1/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.WindowManager;


public class Partida extends android.support.v7.widget.AppCompatImageView {

    private int acel;
    private Bitmap pelota, fondo;
    private int tam_pantX, tam_pantY, posX, posY, velX, velY;
    private int tamPelota;
    boolean pelota_sube;

    /**
     * Crea el contexto, inicializa la posicion de la pelota y determina la dificultad
     * @param contexto Para construir la nueva vista
     * @param nivel_dificultad Determina el nivel de dificultat augmentando la aceleracion
     */
    public Partida(Context contexto, int nivel_dificultad){

        super(contexto);

        WindowManager manejador_ventana=(WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE);

        Display pantalla=manejador_ventana.getDefaultDisplay();

        Point maneja_coord=new Point();   //Integra 2 coordenadas X,Y

        pantalla.getSize(maneja_coord);   //Determina el tamaño de la pantalla donde se ejecuta la app

        tam_pantX=maneja_coord.x;         //Establece el tamaño a las variables

        tam_pantY=maneja_coord.y;

        //Construye un layaut programatico () sobre la imagen que le pasamos
        BitmapDrawable dibujo_fondo=(BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.voley_playa2);

        fondo=dibujo_fondo.getBitmap();// mirar en api getBitmap en clase BitmapDrawable. esto nos lleva a la siguiente instr.

        fondo=Bitmap.createScaledBitmap(fondo, tam_pantX, tam_pantY, false);//mirar en clase Bitmap


        BitmapDrawable objetoPelota=(BitmapDrawable)ContextCompat.getDrawable(contexto, R.drawable.pelota_voley);

        pelota=objetoPelota.getBitmap();

        //Define el tamaño de la pelota con respecto a la pantalla
        tamPelota=tam_pantY/3;

        pelota=Bitmap.createScaledBitmap(pelota, tamPelota, tamPelota, false);

        //Coocamos la pelota en el centro de la pantalla
        posX=tam_pantX/2-tamPelota/2;
        //Colocamos la pelota justo encima de la pantalla(por eso no se ve)
        posY=0-tamPelota;

        //Segun la dificultat augmentara la velocidad
        acel=nivel_dificultad*(maneja_coord.y/400);


    }

    /**
     * Clase que controla el toque en la pantalla
     * @param x Recive cordenada del toque en la pantalla
     * @param y Recive cordenada del toque en la pantalla
     * @return  Por defecto true, false si noo cumple los requisitos
     */
    public boolean toque(int x, int y){

        //Invalida el 1/3 de la pantalla, no se podra clikar en ese espacio
        if(y<tam_pantY/3) return false;

        //Si la pelota esta parada no funciona
        if(velY<=0) return false;

        //Si no hacertamos en la pelota no funcionara el metodo
        if(x<posX || x> posX+tamPelota) return false;

        if(y<posY || y>posY+tamPelota) return false;

        //desplaza la pelota segun los calculos
        velY=-velY;

        double desplX=x-(posX+tamPelota/2);

        desplX=desplX/(tamPelota/2)*velY/2;

        velX+=(int)desplX;

        return true;
    }

    /**
     * Clase que controla el calculo que determina el movimiento de la pelota
     * @return Boolean por defecto false, sera true cuando el juego termine
     */
    public boolean movimientoBola(){

        if(posX<0-tamPelota){

            posY=0-tamPelota;

            velY=acel;
        }

        posX+=velX;

        posY+=velY;

        //controla si la pelota a salido por arriba o por abajo de la pantalla (partida terminada)
        if(posY>=tam_pantY) return true;

        //controla si la pelota a salido por la iz o der (partida terminada)
        if(posX+tamPelota<0 || posX>tam_pantX) return true;


        if(velY<0) pelota_sube=true;

        if(velY>0 && pelota_sube){

            pelota_sube=false;

        }

        velY+=acel;

        return false;
    }

    /**
     * Pinta el fondo y la pelota en el inicio
     * @param lienzo
     */
    protected void onDraw(Canvas lienzo){

        lienzo.drawBitmap(fondo, 0,0, null);

        lienzo.drawBitmap(pelota, posX, posY, null);


    }
}

