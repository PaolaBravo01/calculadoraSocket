package Servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
/**
 * Clase que responde los requerimientos del cliente
 * @author Paola
 *
 */
public class Servidor
{
	/**
	 * Constante que modela el puerto
	 */
	public static int PUERTO = 9000;
	
	
	public static void main(String[] args) throws IOException
	{
		boolean iterar = false; //atributo para indicar si itera o no
		int contador = 0;		//contador
		
		while(iterar == false)
		{
			try
			{
				//variables locales
				double n1 = 0.0;
				double n2 = 0.0;
				String operacion = " ";
				double total = 0.0;
				int temp;
				
				//Canal de comunicacion abierto que espera que el cliente se conecte
				ServerSocket server = new ServerSocket(PUERTO + contador);
				
				System.out.println("Esperando la respuesta del cliente..");
				System.out.println("Esperando la conexión del cliente...");
				
				//Bloquea el programa hasta que llegue una solicitud de conexion
				Socket cliente = server.accept();
				
				System.out.println("Conexion Cliente");
				
				//Escribe los datos que vienen del socket
				ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
				
				//Lee los datos que vienen del socket
				ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
				
				operacion = ois.readUTF(); //lee la cadena que trae el operador matematico
				n1 = ois.readDouble();	   //lee el primer numero ingresado
				n2 = ois.readDouble();	   //lee el segundo numero ingresado	 
				
				//instrucciones para que realice la operacion deseada
				if(operacion.trim().compareToIgnoreCase("+") == 0)
				{
					total = n1 + n2;
				}
				else if(operacion.trim().compareToIgnoreCase("-") == 0)
				{
					total = n1 - n2;
				}
				else if(operacion.trim().compareToIgnoreCase("*") == 0)
				{
					total = n1 * n2;
				}
				else if(operacion.trim().compareToIgnoreCase("/") == 0)
				{
					total = n1 / n2;
				}
				else
				{
					extracted(operacion);
				}
				
				oos.writeDouble(total); //escribe la respuesta de la ooperacion
				oos.flush();			//envia los datos pero no cierra el flujo
				
				temp = ois.readInt();	//lee la respuesta de continuar o no con operaciones
				
				if(temp == 0) //si la respuesta fue no, se termina la ejecucion de la aplicacion
				{
					oos.close();
					ois.close();
			        cliente.close();
			        server.close();

					iterar = true;
				}
				else		//si la respuesta fue si, sigue la ejecucion de la aplicacion
				{					
					oos.close();
					ois.close();
			        cliente.close();
			        server.close();
			        contador ++;
			        
				}
				
			}
			catch (Exception m)
			{
				JOptionPane.showMessageDialog(null, m.getMessage());
				iterar = false;
			}
						
		}
		
	}

	/**
	 * Arroja una excepcion en caso de que el operador sea invalido
	 * @param operacion
	 * @throws Exception el operador es invalido
	 */

	private static void extracted(String operacion) throws Exception {
		throw new Exception("El operador " + "'" +operacion+ "'" + " es invalido." );
	}
	
	
	
	

}
