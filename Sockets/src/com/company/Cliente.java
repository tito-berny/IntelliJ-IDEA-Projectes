package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


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

		//Se ejecuta el metos windows open para que envie al servidor la señal que esta online el cliente
		addWindowListener(new EnvioOnline());
		}	
	
}

			//-------------------------Envio señal Onlone-----------------------
class EnvioOnline extends WindowAdapter{

	//e ejecuta nada mas abrir la ventana del cliente
	public void windowOpened(WindowEvent event){

		try {

			//Creamos socket
			Socket misoket = new Socket("192.168.1.40", 9999);

			//Creamos paquete
			PaqueteEnvio datos = new PaqueteEnvio();

			datos.setMensaje(" online");

			//Creamos flujo datos para que sepa que estamos online nada mas conectemos
			ObjectOutputStream paquete_datos = new ObjectOutputStream(misoket.getOutputStream());

			paquete_datos.writeObject(datos);

			//Cerramos socket
			misoket.close();



		}catch (Exception e){
			System.out.println(e);
		}

	}
			//.--------------------------------------------------------------------------------
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){

		//preguntamos al usuario su nick
		String nick_usuario = JOptionPane.showInputDialog("Nick:");

		//Ponemos en una lavel el nick
		JLabel n_nick = new JLabel("Nick: ");
		add(n_nick);

		nick = new JLabel();
		nick.setText(nick_usuario);
		add(nick);

		JLabel texto=new JLabel("Online : ");
		
		add(texto);

		ip= new JComboBox();
		//ip.addItem("Usuari 1");
		//ip.addItem("Usuari 2");
		//ip.addItem("Usuari 3");

		//ip.addItem("192.168.1.41");
		//ip.addItem("192.168.1.42");

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

				if (!paqueteRecivido.getMensaje().equals(" online")){


				//Escribimos la informacion en el cuador de texto
					campochat.append("\n" + paqueteRecivido.getNick() + ": " + paqueteRecivido.getMensaje());
				}else {

					//campochat.append("\n " + paqueteRecivido.getIps() );

					//Creamos un nuevo arrayList y le agreganos las ips del paquete
					ArrayList<String> IpsMenu = new ArrayList<>();
					IpsMenu = paqueteRecivido.getIps();

					//Borra todos los items del comboBox para que no se sobreescriban los antiguos
					ip.removeAllItems();

					//Recorremos el array lisrt para obtener las ips
					for (String z:IpsMenu){

						ip.addItem(z);
 					}
				}


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
				datos.setIp(ip.getSelectedItem().toString());
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
		
		
	private JTextField campo1;

	private JComboBox ip;

	private JLabel nick;
	
	private JButton miboton;

	private JTextArea campochat;


	
}

class PaqueteEnvio implements Serializable {

	private String nick, ip, mensaje;

	private ArrayList<String> Ips;

	public ArrayList<String> getIps() {
		return Ips;
	}

	public void setIps(ArrayList<String> ips) {
		Ips = ips;
	}

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