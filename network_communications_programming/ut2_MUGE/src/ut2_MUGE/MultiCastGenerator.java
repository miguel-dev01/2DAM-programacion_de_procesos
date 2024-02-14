package ut2_MUGE;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class MultiCastGenerator {

	public static void main(String[] args) {
		try {
			//Lista de Sockets remotos
			List<Socket> clientesRemotos = new LinkedList<>();
			
			final String mCastIp = "224.0.1.1";
			final int mCastPort = 7777;
			InetAddress mCastInet = InetAddress.getByName(mCastIp);
			
			int port = 9999;
			ServerSocket server = new ServerSocket(port);
			
			System.out.println("Bienvenido al servicio de generacion de claves publicas");
			boolean fin = false;
			int id = 0;
			while (!fin) {
				// Aceptamos la conexión
				Socket socket = server.accept();
				clientesRemotos.add(socket);
				String ipCliente = socket.getRemoteSocketAddress().toString();
				System.out.println("Socket " + id + " conectado desde " + ipCliente);
				
				// Pipe que permite enviar de la clase ClienteMultiService a MultiCastEmisor
				PipedWriter emisor = new PipedWriter();
				PipedReader receptor = new PipedReader(emisor);
				PrintWriter flujoSMultiCast = new PrintWriter(emisor);
				// Falta el BufferedReader aqui pero esta inicializado en MultiCastEmisor
				
				// Pipe que va al cliente TCP
				PrintWriter flujoS = new PrintWriter(socket.getOutputStream());
				Scanner flujoE = new Scanner(socket.getInputStream());
				
				// Semaforo
				Semaphore lock = new Semaphore(1);
				
				MultiCastEmisor sender = new MultiCastEmisor(mCastInet, mCastPort, receptor);
				
				// Servicio cliente
			    ClienteMultiService clienteService = 
			    		new ClienteMultiService(socket, flujoS, flujoE, flujoSMultiCast, lock);
				
				id++;
			} // fin bucle
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void finalizarServidor() {
	}

}
