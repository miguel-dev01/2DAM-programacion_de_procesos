package domain;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class MataServer {
	
	private int numClientes;
	static boolean fin = false;

	public MataServer(int numClientes) {
		this.numClientes = numClientes;
	}
	
	@SuppressWarnings("unused")
	public void iniciar() {

		try {
			// Lista de Sockets remotos
			List<Socket> clientesRemotos = new LinkedList<Socket>();
			
			// Para UDP Multicast
			final String mCastIp = "224.0.23.4";
			final int mCastPort = 8823;
			InetAddress mCastInet = InetAddress.getByName(mCastIp);
			
			int port = 8023; // Puerto del servidor de mensajeria
			ServerSocket server = new ServerSocket(port);
			
			System.out.println("Servicio de mensajeria MATA iniciado");
			int id = 1;
			while (!fin) {
				if (id == numClientes) {
					System.out.println("No se aceptan mas conexxiones");
					fin = true;
				}
				// Aceptamos las conexiones
				Socket socket = server.accept();
				clientesRemotos.add(socket);
				String ipCliente = socket.getRemoteSocketAddress().toString();
				System.out.println("Cliente " + id + " conectado");
				
				MataService service = new MataService(id, socket);
				
				id++;
			} // fin bucle
		
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // cierre Iniciar
	
	public static void finalizarMataServer() {
		fin = true;
	}

}
