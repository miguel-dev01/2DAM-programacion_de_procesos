package Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

	public static void main(String[] args) throws IOException {
		
		int port = 8888;
		ServerSocket servidor = new ServerSocket(port);
		Banco banco = new Banco(100); // Con 100 cuentas
		System.out.println("Server running at " + port + "...");
		boolean fin = false;
		int id = 0;
		while (!fin) {
			// Aceptamos la conexion
			Socket socket = servidor.accept();
			// Iniciamos el servicio que gestiona la conexion (Objeto que se ejecutara como un HILO)
			id++;
			BankService servicio = new BankService(id, socket, banco); // el hilo
		}

	}

}
