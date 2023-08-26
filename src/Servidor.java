

import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.net.*;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);

		Thread mihilo= new Thread(this);
		mihilo.start();

		
		}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("Estoy a la escucha");
		try {
			ServerSocket servidor = new ServerSocket(5000);

			String name, ip, mensaje;

			EnvioCompleto paquete_recibido;
			

			while(true){

				Socket misocket= servidor.accept();
				ObjectInputStream paquete_datos= new ObjectInputStream(misocket.getInputStream());

				paquete_recibido= (EnvioCompleto) paquete_datos.readObject();

				name= paquete_recibido.getName();
				ip= paquete_recibido.getIp();
				mensaje= paquete_recibido.getMensaje();



				/*DataInputStream flujo_entrada= new DataInputStream(misocket.getInputStream());

				String mensaje_texto=flujo_entrada.readUTF();

				areatexto.append("\n" + mensaje_texto);*/
				areatexto.append("\n"+ name+ ": " + mensaje + "Enviado a: " +ip);

				Socket enviaDestinatario= new Socket(ip, 5020);

				ObjectOutputStream paqueteReenvio= new ObjectOutputStream(enviaDestinatario.getOutputStream());
				paqueteReenvio.writeObject(paqueteReenvio);
				paqueteReenvio.close();
				enviaDestinatario.close();

				misocket.close();

			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private	JTextArea areatexto;
}
