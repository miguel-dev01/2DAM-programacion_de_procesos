package ut2_MENSA;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


@SuppressWarnings("unused")
public class SRIService extends Thread {

	private int id;
	private Socket socket;
	private PrintWriter flujoS;
	private Scanner flujoE;
	private int numCliente;
	private boolean asignado; // booleano para indicar si el cliente ya tiene un numero asignado
	
	public SRIService(int id, Socket socket) throws IOException {
		this.id = id;
		this.socket = socket;
		this.flujoS = new PrintWriter(socket.getOutputStream());
		this.flujoE = new Scanner(socket.getInputStream());
		this.numCliente = 0;
		this.asignado = false;
		
		this.start();
	}
	
	public void run() {
		flujoS.println("Bienvenido al servicio de mensajeria MENSA.");
		flujoS.println("Indica los comandos(cliente/mensa <numero cliente> <MENSAJE>/quit)");
		flujoS.flush();
		try {
			boolean fin = false;
			while (!fin) {
				String comando = flujoE.nextLine();
				System.out.println(comando);
				
				if (comando.contains("quit")) {
					fin = true;
				} else if (comando.equals("cliente")) {
					if (!asignado) {
						numCliente = ServerMensa.nextClient();
						flujoS.println("Numero de cliente asignado a " + numCliente);
						flujoS.flush();
						asignado = true;
					} else {
						flujoS.println("Ya tienes un numero de cliente asignado.");
						flujoS.flush();
					}
				} else if (comando.contains("mensa")) {
					if (numCliente == 0) {
						flujoS.println("No tienes numero de cliente asignado.");
						flujoS.flush();
					} else {
						// Se da por hecho que el formato del mensa es correcto, tal y como se indica:
						// mensa <numero cliente> <MENSAJE>. Se puede controlar el error comprobando si al
						// hacer el split, el array partes, tiene como longitud 3, si no, se indica que no se 
						// ha introducido el formato de mensaje correcto.
				        // Dividir la cadena en partes utilizando el espacio como delimitador
				        String[] partes = comando.split(" ");
				        // Acceder al segundo elemento que debería ser el número
				        String numeroClienteMensa = partes[1];
						if (Integer.parseInt(numeroClienteMensa) != numCliente) {
							flujoS.println("No es tu numero de cliente");
							flujoS.flush();
						} else {
							String mensa = comando.replace("mensa", "Cliente:");
							SRIServiceUDP servidorUDP = 
									new SRIServiceUDP(InetAddress.getByName("224.0.1.1"), 7777, mensa);
							mensa = "";
						}
					}
				} else {
					flujoS.println("Comando no reconocido");
					flujoS.flush();
				}
			} // cierre While
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		flujoE.close();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
