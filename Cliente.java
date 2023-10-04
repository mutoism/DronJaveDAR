/*
 * Universidad de Granada
 * ETS Ingenierías de Informática y Telecomunicaciones
 * Autor: Alejandro Rosa Sarabia
 * Curso: 3º
 * Asignatura: Desarrollo de Aplicaciones en Red
 */
package practica2_dron;

import java.io.BufferedReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Base64;



public class Cliente {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Scanner entrada = new Scanner(System.in);
        String host = "localhost"; // Cambiar por la dirección IP o el nombre de dominio del servidor
        int puerto =9999; // Cambiar por el número de puerto utilizado por el servidor
        // Creamos un objeto Socket para conectarnos al servidor
        Socket socket = new Socket(host, puerto);
        System.out.println("Conectado al servidor " + host + " en el puerto " + puerto);
        // Creamos objetos para leer y escribir en el socket
        PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      
        
        System.out.print("Ingrese su nombre de usuario: ");
        String usuario = entrada.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = entrada.nextLine();
        String hash = Servidor.md5(contrasena);
        byte[] hashBytes = hash.getBytes();
        
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] hashBytesCod = encoder.encode(hashBytes);
        
        String hashCodificado = new String(hashBytesCod);
        
        escritor.println("Credenciales "+usuario+" "+hashCodificado);
        escritor.flush();
        
        String respuesta = lector.readLine();
        System.out.println(respuesta);
       
        boolean parada = true;
       while(parada){
           String orden = entrada.nextLine();
           escritor.println(orden);
           escritor.flush();
           String respuesta2 = lector.readLine();
           System.out.println(respuesta2);
           if(respuesta2.compareTo("Cierre sesion")==0){
               parada = false;
           
            }
       }
       socket.close ();
 
        
        
    }
}


