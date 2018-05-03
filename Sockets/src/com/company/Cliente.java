package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){

		nick =new JTextField(5);
		add(nick);
	
		JLabel texto=new JLabel("-CHAT-");
		
		add(texto);

		ip=new JTextField(8);
		add(ip);
		campochat=new JTextArea(12,20);

		add(campochat);
	
		campo1=new JTextField(20);
	
		add(campo1);
	
		miboton=new JButton("Enviar");

		EnviaTexto mievento = new EnviaTexto();

		miboton.addActionListener(mievento);

		add(miboton);

		//Creamos un nuevo hilo
		Thread mihilo = new Thread(this);

		//Ejecutamos el hilo
		mihilo.start();

	}

	@Override
	public void run() {
		try {

			//Creamos una nueva instancia de ServerSocket y le indicamos el puerto a la escucha
			ServerSocket servido_cliente = new ServerSocket(9090);

			//Creamos el socket
			Socket cliente;
			//Variable del paquete
			PaqueteEnvio paqueteRecivido;

			//Bucle para que siempre este a la escucha
			while (true){

				//Le decimos que acepte todas las conecciones que reciva
				cliente = servido_cliente.accept();

				//Creamos el flujo de datos para transportar e paquete
				ObjectInputStream flujo_entrada = new ObjectInputStream(cliente.getInputStream());

				//Obtenemos la informacion del paquete casteamos
				paqueteRecivido = (PaqueteEnvio) flujo_entrada.readObject();

				//Escribimos la informacion en el cuador de texto
				campochat.append("\n" + paqueteRecivido.getNick() + ": " + paqueteRecivido.getMensaje());

			}


		}catch (Exception e){
			System.out.println(e);
		}
	}


	private class EnviaTexto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			System.out.print(campo1.getText());

			//Ponemos nuestro mensaje en nuestr app del cliente
			campochat.append("\n" + "YO: " + campo1.getText());

			try {
				//Creamos un nuevo socket, tenemos que indcarle la direccion IP local y el puerto
				Socket miSoket = new Socket("192.168.1.40", 9999);

				//Creamos una nueva instancia del objeto
				PaqueteEnvio datos = new PaqueteEnvio();

				//Encapsulamos las variables de nick, ip y mensaje en el nuevo onjeto
				datos.setNick(nick.getText());
				datos.setIp(ip.getText());
				datos.setMensaje(campo1.getText());

				//Necesitamos utilizzar un ObjectOutputStream para poder enviar un objeto
				ObjectOutputStream paquete_datos = new ObjectOutputStream(miSoket.getOutputStream());

				//Enviamos el paquete
				paquete_datos.writeObject(datos);

				//Cerramos conecsion
				miSoket.close();

				/*
				//Creamos el flujo de salida, tenemos que indicarle el socket por el que va a viajar
				DataOutputStream flujo_salida = new DataOutputStream(miSoket.getOutputStream());

				//Guarda en el flujo lo que haya en el campo de texto.
				flujo_salida.writeUTF(campo1.getText());

				//Cerramos el flujo de datos
				flujo_salida.close();*/

			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.print(e1.getMessage());

			}



		}
	}
		
		
	private JTextField campo1, nick, ip;
	
	private JButton miboton;

	private JTextArea campochat;


	
}

class PaqueteEnvio implements Serializable {

	private String nick, ip, mensaje;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}