

import javax.swing.*;

import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

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

		name= new JTextField(5);
		add(name);
	
		JLabel texto=new JLabel(" CHAT CLIENTE");
		add(texto);

		ip= new JTextField(8);
		add(ip);

		campochat= new JTextArea(12,20) ;
		add(campochat);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");

		EnviaTexto mievento= new EnviaTexto();
		miboton.addActionListener(mievento);
		
		add(miboton);	
		
	}
	
	private class EnviaTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//System.out.println ("Hola funciona");

			try {
				Socket misocket= new Socket("192.168.100.16", 5000);
				
				EnvioCompleto datos= new EnvioCompleto();
				datos.setName(name.getText());
				datos.setIp(ip.getText());
				datos.setMensaje(campo1.getText());
				ObjectOutputStream paquete_Datos=new ObjectOutputStream(misocket.getOutputStream());

				paquete_Datos.writeObject(datos);
				misocket.close();


				/*DataOutputStream flujo_salida= new DataOutputStream(misocket.getOutputStream());

				flujo_salida.writeUTF(campo1.getText());
				flujo_salida.close(); */

			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println (e1.getMessage());
			} 
		}
	
	}

		
	private JTextField campo1, name, ip;

	private JTextArea campochat;
	
	private JButton miboton;
	
}

class EnvioCompleto implements Serializable{

	private String name, ip, mensaje;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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