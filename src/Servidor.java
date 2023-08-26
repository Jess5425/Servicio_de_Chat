import javax.swing.*; 
 
import java.awt.*; 
import java.io.*; 
import java.net.*; 
 
/** 
 * Clase principal que inicia  y 
 * actúa como punto de entrada 
 * de la aplicación del servidor 
 */ 
 
public class Servidor  { 
 
  public static void main(String[] args) { 
      
    /** 
     * Se crea una instancia de MarcoServidor y se configura la acción de cierre 
     */ 
    MarcoServidor mimarco=new MarcoServidor(); 
     
    mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
       
  }   
} 
 
class MarcoServidor extends JFrame implements Runnable{ 
   
  public MarcoServidor(){ 
     
    /** 
     * Se establecen las dimensiones y posición de la ventana 
     */ 
 
    setBounds(1200,300,280,350);         
       
    JPanel milamina= new JPanel(); 
     
    milamina.setLayout(new BorderLayout()); 
     
     /** 
     * Se crea un JTextArea para mostrar los mensajes 
     */ 
 
    areatexto=new JTextArea(); 
     
    milamina.add(areatexto,BorderLayout.CENTER); 
     
    add(milamina); 
     
    setVisible(true); 
 
     /** 
     * Se inicia un hilo para ejecutar el método run en segundo plano 
     */ 
    Thread mihilo= new Thread(this); 
    mihilo.start(); 
 
     
    } 
   
  @Override 
  public void run() { 
 
    try { 
      ServerSocket servidor = new ServerSocket(5000); 
 
      String name, ip, mensaje; 
 
      EnvioCompleto paquete_recibido; 
       
 
      while(true){ 
 
        Socket misocket= servidor.accept(); 
        /** 
         *Se crea un ObjectInputStream para leer el objeto recibido   
         */  
        ObjectInputStream paquete_datos= new ObjectInputStream(misocket.getInputStream()); 
 
        paquete_recibido= (EnvioCompleto) paquete_datos.readObject(); 
 
        /** 
         * Aquí se obtienen los datos del paquete recibido 
         */ 
        name= paquete_recibido.getName(); 
        ip= paquete_recibido.getIp(); 
        mensaje= paquete_recibido.getMensaje(); 
 
        areatexto.append("\n"+ name+ ": " + mensaje + "Enviado a: " +ip); 
 
        Socket enviaDestinatario= new Socket(ip, 5020); 
 
        ObjectOutputStream paqueteReenvio= new ObjectOutputStream(enviaDestinatario.getOutputStream()); 
        paqueteReenvio.writeObject(paqueteReenvio); 
        paqueteReenvio.close(); 
        enviaDestinatario.close(); 
 
        misocket.close(); 
 
      } 
 
    } catch (IOException | ClassNotFoundException e) { 
     /** 
       * Se manejan excepciones de entrada/salida y de clase no encontrada 
       */ 
      e.printStackTrace(); 
    } 
  } 
 
  private  JTextArea areatexto; 
 /** 
   * Área de texto para mostrar mensajes 
   */      
}