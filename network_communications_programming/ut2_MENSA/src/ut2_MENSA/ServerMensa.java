package ut2_MENSA;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


public class ServerMensa {
	
	static int numCliente = 0;

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		try {
			//Lista de Sockets remotos
			List<Socket> clientesRemotos = new LinkedList<Socket>();
			
//			final String mCastIp = "224.0.1.1";
//			final int mCastPort = 7777;
//			InetAddress mCastInet = InetAddress.getByName(mCastIp);
			
			int port = 8899; // Puerto del servidor de mensajeria
			ServerSocket server = new ServerSocket(port);
			
			System.out.println("Bienvenido al servicio de mensajeria MENSA");
			boolean fin = false;
			int id = 1;
			while (!fin) {
				// Aceptamos las conexiones
				Socket socket = server.accept();
				clientesRemotos.add(socket);
				String ipCliente = socket.getRemoteSocketAddress().toString();
				System.out.println("Socket " + id + " conectado desde " + ipCliente);
				
				
				SRIService servicioCliente = new SRIService(id, socket);
				
				id++;
			} // fin bucle
		
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // cierre Main
	
	public static int nextClient() {
		numCliente++;
		return numCliente;
	}

}
