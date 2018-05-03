package com.company;

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable {
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);

		//Creamos un hilo
		Thread mihilo = new Thread(this);
		//Iniciamos el hilo
		mihilo.start();
		
		}
	
	private	JTextArea areatexto;

	@Override
	public void run() {

		//System.out.println("Estoy a la escucha");
		//Creamos un ServerSocket
		//Creamos un ServerSocket para poner la aplicación a la escucha,
		// con uno un constructores que nos pedirá un Int para indicar qué puerto está a la escucha.
		try {
			ServerSocket servidor = new ServerSocket(9999);

			//Variables para guardar la informacion del cliente
			String nick, ip, mensaje;

			//Instanciapara el paquete
			PaqueteEnvio paqueteRecivido;


			//Creamos un bucle infinito para que siempre acepte la s conecsiones
			while (true){

				//De decimos que acepte las conecsiones que le vengan en servidor
				Socket misocket = servidor.accept();

				//Flujo de datos de entrada
				ObjectInputStream paquete_datos = new ObjectInputStream(misocket.getInputStream());

				//Casteamos del objeto a la clase paqueteEnvio
				paqueteRecivido = (PaqueteEnvio) paquete_datos.readObject();

				//Obtenemos las variables del objeto y las enapsulamos en las variables locales
				nick = paqueteRecivido.getNick();
				ip = paqueteRecivido.getIp();
				mensaje = paqueteRecivido.getMensaje();

				/*
				//Recibe el flujo de datos
				DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());

				//Creamos un STRing para que reciba el flujo de texto
				String mensajeTexto = flujo_entrada.readUTF();

				//Escribimos en el textArea el texto del Flujo de datos
				areatexto.append("\n" + mensajeTexto);
				*/

				//mostramos la informacion recivida en el text area
				areatexto.append("\n" + nick + " : " + mensaje + "   para :" + ip);

				//Creamos un socket para enviar la informacion recivida al destinatario
				Socket enviaDestinatario = new Socket(ip, 9090);

				//Creamos el paquete por el socket creado
				ObjectOutputStream paquete_reenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());

				//Ponemos la informacion dentro del paquete
				paquete_reenvio.writeObject(paqueteRecivido);

				//Cerramos coneccion
				paquete_reenvio.close();

				//Cerramos conecsion paquete
				enviaDestinatario.close();

				//Cerramos la conecsion
				misocket.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
