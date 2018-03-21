package Servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Servidor
{
	
	public static int PUERTO = 9000;
	
	
	public static void main(String[] args) throws IOException
	{
		boolean iterar = false;
		int contador = 0;
		
		while(iterar == false)
		{
			try
			{
				double n1 = 0.0;
				double n2 = 0.0;
				String mensaje = " ";
				double total = 0.0;
				int temp;
				
				ServerSocket server = new ServerSocket(PUERTO + contador);
				
				System.out.println("Enlazado");
				System.out.println("Esperando");
				System.out.println("Sigo Esperando.....");
				
				Socket cliente = server.accept();
				
				System.out.println("Conexion Cliente");
				
				ObjectOutputStream in = new ObjectOutputStream(cliente.getOutputStream());
				ObjectInputStream out = new ObjectInputStream(cliente.getInputStream());
				
				mensaje = out.readUTF();
				n1 = out.readDouble();
				n2 = out.readDouble();
				
				if(mensaje.trim().compareToIgnoreCase("+") == 0)
				{
					total = n1 + n2;
				}
				else if(mensaje.trim().compareToIgnoreCase("-") == 0)
				{
					total = n1 - n2;
				}
				else if(mensaje.trim().compareToIgnoreCase("*") == 0)
				{
					total = n1 * n2;
				}
				else if(mensaje.trim().compareToIgnoreCase("/") == 0)
				{
					total = n1 / n2;
				}
				else
				{
					Exception m = new Exception("El operador " + "'" +mensaje+ "'" + " no corresponde a una operación matemática." );
					throw m;
				}
				
				in.writeDouble(total);
				in.flush();
				
				temp = out.readInt();
				
				if(temp == 0)
				{
					in.close();
			        out.close();
			        cliente.close();
			        server.close();

					iterar = true;
				}
				else
				{					
					in.close();
			        out.close();
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
	
	
	
	

}
