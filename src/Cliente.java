import javax.swing.*; 
 
import java.awt.event.*; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.io.Serializable; 
import java.net.*; 
 
/** 
* Clase principal que contiene el metodo main  
* para iniciar la aplicación 
* y actua como su punto de entrada 
*/ 
 
public class Cliente { 
 
  public static void main(String[] args) { 
 
    /** 
     * Se crea el objeto MarcoCliente para iniciar la inerfaz gráfica 
     */ 
 
    MarcoCliente mimarco=new MarcoCliente(); 
     
    mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
 
  } 
 
} 
 
/** 
* Clase que define la ventana principal  
* de la interfaz gráfica y hace visible el marco 
*/ 
 
class MarcoCliente extends JFrame{ 
   
  public MarcoCliente(){ 
     
    /** 
     * dimensiones de la ventana 
     */ 
     
    setBounds(600,300,280,350); 
         
    LaminaMarcoCliente milamina=new LaminaMarcoCliente(); 
     
    add(milamina); 
     
    setVisible(true); 
    }   
   
} 
 
class LaminaMarcoCliente extends JPanel implements Runnable{ 
   
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
 
     /* 
     * Se agrega un ActionListener para manejar el evento del botón 
     */ 
 
    EnviaTexto mievento= new EnviaTexto(); 
    miboton.addActionListener(mievento); 
     
    add(miboton);   
     
    /* 
     * Crear y empezar un nuevo hilo para la lámina 
     */ 
    Thread mihilo= new Thread(this); 
    mihilo.start(); 
     
  } 
   
   /** 
   *Se maneja el boton de "enviar" 
   */ 
 
  private class EnviaTexto implements ActionListener{ 
 
    @Override 
    public void actionPerformed(ActionEvent e) { 

      campochat.append("\n"+ campo1.getText()); 
 
      try { 
        /** 
         *Se crea un socket para enviar los datos al servidor   
         */ 
        Socket misocket= new Socket("192.168.100.16", 5000); //Esta ip es respecto a la PC que se quiera usar. 
         
        EnvioCompleto datos= new EnvioCompleto(); 
        datos.setName(name.getText()); 
        datos.setIp(ip.getText()); 
        datos.setMensaje(campo1.getText()); 
        ObjectOutputStream paquete_Datos=new ObjectOutputStream(misocket.getOutputStream()); 
 
        paquete_Datos.writeObject(datos); 
        misocket.close(); 

 
      } catch (UnknownHostException e1) { 
       
        e1.printStackTrace(); 
      } catch (IOException e1) { 
        
        System.out.println (e1.getMessage()); 
      }  
    } 
   
  } 
 
/** 
 * Declaración de variables para los elementos de la lámina 
 */ 
  private JTextField campo1, name, ip; 
 
  private JTextArea campochat; 
   
  private JButton miboton; 
 
  @Override 
  public void run() { 
    
 
    try { 
    /** 
     * Se crea un servidor para recibir conexiones 
     */  
      ServerSocket servidor_cliente= new ServerSocket(5020); 
 
      Socket cliente; 
      EnvioCompleto paqueteRecibido; 
     
       
      while(true){ 
        cliente= servidor_cliente.accept(); 
 
        ObjectInputStream flujoentrada= new ObjectInputStream(cliente.getInputStream()); 
        paqueteRecibido= (EnvioCompleto) flujoentrada.readObject(); 
 
        /** 
         * Agregar el mensaje recibido al área de chat 
         */ 
        campochat.append("\n" + paqueteRecibido.getName()+ ": "+ paqueteRecibido.getMensaje()); 
 
 
      } 
 
       
    } catch (Exception e) { 
        /** 
         * Maneja excepciones generales 
         */ 
      System.out.println(e.getMessage()); 
    } 
 
 
  }


} 
 
/** 
 * Clase que define el objeto enviado  
 * con datos a través de la red  
 * Contiene campos para el nombre del cliente, su dirección IP  
 * y el mensaje que quiere enviar 
*/ 
 
class EnvioCompleto implements Serializable{ 
 
  private String name, ip, mensaje; 
 
  /** 
   * Métodos getter y setter para los atributos 
   */ 
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

