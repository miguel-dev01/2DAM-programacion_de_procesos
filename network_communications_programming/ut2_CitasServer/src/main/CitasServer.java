package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import domain.MesaService;

public class CitasServer {

	public static void main(String[] args) throws IOException {
	
		// Lista de sockets remotos
		var clientesRemotos = new LinkedList<Socket>();
		int port = 8888;
		ServerSocket servidor = new ServerSocket(port);
		
		System.out.println("Server running at " + port + "...");
		boolean fin = false;
		int id = 0;
		while (!fin) {
			// Aceptamos la conexion
			Socket socket = servidor.accept();
			
			clientesRemotos.add(socket);
			String ipCliente = socket.getRemoteSocketAddress().toString();
			
			// Iniciamos el servicio que gestiona la conexion
			MesaService mesaService = new MesaService(id, socket);
			id++;
		}
		
		
		servidor.close();
	}
	
}
