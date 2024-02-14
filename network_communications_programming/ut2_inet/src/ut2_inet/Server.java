package ut2_inet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		
		ServerSocket servidor = new ServerSocket(8888);
		System.out.println("Server running...");
		boolean fin = false;
		while (!fin) {
			Socket socket = servidor.accept();
			System.out.println("Conexion desde " + socket.getInetAddress().getHostAddress());
		}
		
	}
}
