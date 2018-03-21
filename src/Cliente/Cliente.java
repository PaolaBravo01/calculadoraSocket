package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.text.IconView;

import jdk.nashorn.internal.runtime.regexp.joni.exception.JOniException;
import sun.awt.IconInfo;

public class Cliente
{
	public static final String HOST = "localhost";
	
	public static int PUERTO = 9000; 
	
	
	public static void main(String[] args)throws IOException
	{
		
		boolean iterar = false;
		String verificar = " ";
		int contador = 0;
		
		while( iterar == false )
		{	
			try
			{	
				double n1;
				double n2;
				String mensaje;
				double total;
				
				Socket cliente = new Socket (HOST,PUERTO + contador);
				System.out.println("Conecto");
				
				ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
				ObjectOutputStream	out = new ObjectOutputStream(cliente.getOutputStream());
				n1 = Double.parseDouble(JOptionPane.showInputDialog("Digite el primer numero"));
				mensaje = JOptionPane.showInputDialog("Indique el símbolo de la operación:  \n + para sumar \n - para restar \n * para multiplicar \n / para dividir ");
			   	n2 = Double.parseDouble(JOptionPane.showInputDialog("Digite el segundo Numero"));
			   	
			   	out.writeUTF(mensaje);
			   	out.writeDouble(n1);
			   	out.writeDouble(n2);
			   	out.flush();
			   	
			   	total = in.readDouble();
		        JOptionPane.showMessageDialog(null, ("El resultado de " + "\n" + n1 + " " + mensaje + " " + n2 + " = " + total), "Resultado de la operación:", 1 , null );
		        
		        verificar = JOptionPane.showInputDialog("¿Quiere realizar un cálculo? (responder: si o no.)");
		        
				if(verificar.compareTo("no") == 0)
				{
					out.writeInt(0);
					out.flush();
					
					in.close();
			        out.close();
			        cliente.close();
			        
					iterar = true;
				}
				else
				{
					out.writeInt(1);
					out.flush();
					
					in.close();
			        out.close();
			        cliente.close();
			        contador ++;
				}
				
				
			}
			catch (Exception m)
			{
				JOptionPane.showMessageDialog(null, m.getMessage());
				iterar = true;
			}

		}
		
        
	}
	
	
	
}
