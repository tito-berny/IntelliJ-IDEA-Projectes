package com.company;

import java.awt.geom.*;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

class Main {



    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //Crea un nuevo marco
        JFrame marco=new MarcoRebote();
        //Asigna la ventana
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Inicializa la ventana
        marco.setVisible(true);

    }

}




//Movimiento de la pelota-----------------------------------------------------------------------------------------

class Pelota{

    // Mueve la pelota invirtiendo posici�n si choca con l�mites

    public void mueve_pelota(Rectangle2D limites){

        x+=dx;

        y+=dy;

        if(x<limites.getMinX()){

            x=limites.getMinX();

            dx=-dx;
        }

        if(x + TAMX>=limites.getMaxX()){

            x=limites.getMaxX() - TAMX;

            dx=-dx;
        }

        if(y<limites.getMinY()){

            y=limites.getMinY();

            dy=-dy;
        }

        if(y + TAMY>=limites.getMaxY()){

            y=limites.getMaxY()-TAMY;

            dy=-dy;

        }

    }

    //Forma de la pelota en su posicion inicial

    public Ellipse2D getShape(){

        return new Ellipse2D.Double(x,y,TAMX,TAMY);

    }

    private static final int TAMX=15;

    private static final int TAMY=15;

    private double x=0;

    private double y=0;

    private double dx=1;

    private double dy=1;


}

// Lamina que dibuja las pelotas----------------------------------------------------------------------


class LaminaPelota extends JPanel{

    //A�adimos pelota a la l�mina

    public void add(Pelota b){

        pelotas.add(b);
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2=(Graphics2D)g;

        for(Pelota b: pelotas){

            g2.fill(b.getShape());
        }

    }

    private ArrayList<Pelota> pelotas=new ArrayList<Pelota>();
}

class Pelotahilos implements Runnable{

    //CONSTRUCTOR
    public Pelotahilos (Pelota unaPelota, Component unComponent){

        pelota = unaPelota;

        componente = unComponent;

    }

    private Pelota pelota;
    private Component componente;

    //METODO RUN
    @Override
    public void run() {

        //Thread.interrupted debuelve un boolean si esta o no interrumpido el hilo
        System.out.println("Stode del hilo al comnzar " + Thread.interrupted());

        //Bucle para el movimiento de la pelota sera infinito
        //TODO SI EL HILO NO ESTA INTERRUMPIDO
        while (!Thread.currentThread().isInterrupted()){

            pelota.mueve_pelota(componente.getBounds());

            //Necesitamos usar try catch para sleep
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                //e.printStackTrace();

                //TODO IMPORTANTE DETIENE EL HILO en un catch por el sleep
                //Cuando salta la exepcion al no poder detener un hilo bloqueado por sleep
                //En la exepcion interrumpimos el hilo
                Thread.currentThread().interrupt();

                //System.out.println("Hilo bloqueado imposible su interrupcion");
            }
            componente.paint(componente.getGraphics());

        }

        System.out.println("Stado del hilo al finaizar " + Thread.interrupted());


    }
}

//Marco con lamina y botones------------------------------------------------------------------------------

class MarcoRebote extends JFrame{

    private LaminaPelota lamina;

    //Declaramos variables t y botones para que sea accesibe desde cualquier metodo todos los hilos

    private static Thread t1,t2,t3;

    public static JButton arranca1, arranca2,arranca3, detener1, detener2, detener3;

    public MarcoRebote(){

        setBounds(600,300,600,350);

        setTitle ("Rebotes");

        lamina=new LaminaPelota();

        add(lamina, BorderLayout.CENTER);

        JPanel laminaBotones=new JPanel();


        //-------------------------BOTONES PARA INICIAR CADA HILO-------------------------

        //BOTON1
        arranca1= new JButton("hilo1");

        arranca1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    comienza_el_juego(e);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        laminaBotones.add(arranca1);

        //BOTON2
        arranca2= new JButton("hilo2");

        arranca2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    comienza_el_juego(e);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        laminaBotones.add(arranca2);

        //BOTON3
        arranca3= new JButton("hilo3");

        arranca3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    comienza_el_juego(e);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        laminaBotones.add(arranca3);


        //-------------------------BOTONES PARA DETENER CADA HILO-------------------------


        //BOTON DETENER 1
        detener1= new JButton("detener1");

        detener1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detener(e);
            }
        });

        laminaBotones.add(detener1);

        //BOTON DETENER 2
        detener2= new JButton("detener2");

        detener2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detener(e);
            }
        });

        laminaBotones.add(detener2);

        //BOTON DETENER 3
        detener3= new JButton("detener3");

        detener3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detener(e);
            }
        });

        laminaBotones.add(detener3);

        add(laminaBotones, BorderLayout.SOUTH);
    }


    //Ponemos botones

    public void ponerBoton(Container c, String titulo, ActionListener oyente){

        JButton boton=new JButton(titulo);

        c.add(boton);

        boton.addActionListener(oyente);

    }

    //Añade pelota y la bota 1000 veces

    public void comienza_el_juego (ActionEvent e) throws InterruptedException {


        Pelota pelota=new Pelota();

        lamina.add(pelota);

        //Instanciamos la clase
        Runnable r = new Pelotahilos(pelota,lamina);

        //Thread t = new Thread(r);

        //Dependiendo de que boton hemos pulsado se inicia un hilo o otro
        if (e.getSource().equals(arranca1)){
            t1 = new Thread(r);

            t1.start();
        }else if (e.getSource().equals(arranca2)){
            t2 = new Thread(r);

            t2.start();
        }else if (e.getSource().equals(arranca3)){
            t3 = new Thread(r);

            t3.start();
        }

    }

    /**
     * Detiene un hilo cuando le damos al boton detener
     */
    public void detener(ActionEvent e){

        //Dependiendo de que boton de detener pulsemos detienee un hilo o otro
        if(e.getSource().equals(detener1)){

            //Interrumpimos el hilo en ejecucion
            t1.interrupt();
        }else if(e.getSource().equals(detener2)){
            t2.interrupt();
        }else if(e.getSource().equals(detener3)){
            t3.interrupt();
        }
        //Detiene el hilo pero esta obsoleto
        //t.stop();

    }


}
