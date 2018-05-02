package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

class LaminaMarcoCliente extends JPanel{
	
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

	}
	
	
	
	private class EnviaTexto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			System.out.print(campo1.getText());


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

class PaqueteEnvio {

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