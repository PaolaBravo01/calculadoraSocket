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
/**
 * Clase que indica los procedimientos del cliente
 * @author Paola
 *
 */

public class Cliente

{
	/**
	 * Constante que modela el servidor
	 */
	public static final String SERVIDOR = "localhost";
	
	/**
	 * Constante que modela el puerto
	 */
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
				//variables locales
				double n1;			
				double n2;
				String operador;
				double total;
				
				//Canal de comunicacion con el servidor, especificando la direccion IP y el puerto
				Socket cliente = new Socket (SERVIDOR,PUERTO + contador);
				System.out.println("Conecto");

				//Lee los datos que vienen del socket
				ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
				//Escribe los datos que vienen del socket
				ObjectOutputStream	out = new ObjectOutputStream(cliente.getOutputStream());
				
				//Pregunta por el primer n�mero
				n1 = Double.parseDouble(JOptionPane.showInputDialog("Digite el primer n�mero"));
				
				//Pregunta por el segundo n�mero
			   	n2 = Double.parseDouble(JOptionPane.showInputDialog("Digite el segundo n�mero"));
				
			   	//Pregunta por la operacion que se desea realizar
			   	operador = JOptionPane.showInputDialog("Indique la operaci�n a realizar:  \n Para suma: + \n Para resta: - \n Para multiplicaci�n: * \n Para divisi�n: / ");

			   	
			   	out.writeUTF(operador);	//escribe la cadena que trae el operador matematico
			   	out.writeDouble(n1);	//escribe el primer n�mero
			   	out.writeDouble(n2);	//escribe el segundo n�mero
			   	out.flush();			//envia los datos pero no cierra el flujo
			   	
			   	//Lee el resultado operaci�n
			   	total = in.readDouble();	
			   	
			   	//Muestra el resultado de la operaci�n
		        JOptionPane.showMessageDialog(null, "El resultado es: " + "\n" + n1 + " " + operador + " " + n2 + " = " + total);
		        
		        //Pregunta si se quiere realizar mas operaciones
		        verificar = JOptionPane.showInputDialog("�Desea realizar una operaci�n matematica? \n Digite S para si \n Digite N para no");
		        
		        //Si la respuesta es no, se detiene la ejecuci�n 
				if(verificar.compareTo("N") == 0)
				{
					out.writeInt(0);
					out.flush();
					
					in.close();
			        out.close();
			        cliente.close();
			        
					iterar = true;
				}
				else				        //Si la respuesta es si, continua la ejecuci�n
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
