package com.example.berny.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

import java.text.DecimalFormat;

/**
 * Created by Juan on 09/05/2017.
 */

public class NivelPantalla extends AppCompatImageView {

    int lado, radio, radioPeq, trazo;

    float[] angulos; // Recibirá las variaciones del sensor

    Bitmap fondo;  //  dibujo de fondo

    Paint trazoDibujo;

    Bitmap burbuja;

    public NivelPantalla(Context contexto, int lado){   // recibe el tamaño del espacio de dibujo con "lado"

        super(contexto);

        this.lado=lado;

        radio=lado/2;

        radioPeq=lado/10;

        trazo=lado/100;

        angulos=new float[2];

        angulos[0]=0;

        angulos[1]=0;

        fondo=iniciaFondo();

        trazoDibujo=new Paint();

        trazoDibujo.setColor(Color.BLACK);

        trazoDibujo.setTextSize(20);

        BitmapDrawable bola=(BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.burbuja);

        burbuja=bola.getBitmap();

        burbuja=Bitmap.createScaledBitmap(burbuja, radioPeq*2, radioPeq*2, true);

    }


    private Bitmap iniciaFondo(){

        Bitmap.Config conf=Bitmap.Config.ARGB_4444;

        Bitmap fondo=Bitmap.createBitmap(lado, lado, conf);

        Canvas lienzo=new Canvas(fondo);

        Paint lapiz=new Paint();

        lapiz.setColor(Color.RED);

        lienzo.drawCircle(radio, radio, radio, lapiz);

        lapiz.setColor(Color.BLACK);

        lienzo.drawCircle(radio, radio, radio-trazo, lapiz);

        lapiz.setColor(Color.RED);

        lienzo.drawCircle(radio, radio, radioPeq+trazo, lapiz);

        lapiz.setStrokeWidth(trazo);

        lienzo.drawLine(radio, 0, radio, lado, lapiz);

        lienzo.drawLine(0, radio, lado, radio, lapiz);

        return fondo;

    }

    public void angulos(float[] angulos){

        this.angulos=angulos;

        invalidate();

    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  // ajustamos dimensiones de la vista (no trabaja a pantalla completa)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(lado, lado);

    }

    protected void onDraw(Canvas lienzo){

        super.onDraw(lienzo);

        lienzo.drawBitmap(fondo, 0, 0, null);

        int posX=radio-radioPeq+(int)(angulos[0]/10*radio);

        int posY=radio-radioPeq-(int)(angulos[1]/10*radio);

        lienzo.drawBitmap(burbuja, posX, posY, null);


    }

}