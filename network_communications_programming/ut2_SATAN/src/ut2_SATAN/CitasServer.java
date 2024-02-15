package ut2_SATAN;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


public class CitasServer {

	public static void main(String[] args) {
		try {
			//Lista de Sockets remotos
			List<Socket> clientesRemotos = new LinkedList<>();
			
			final String mCastIp = "224.0.1.1";
			final int mCastPort = 7777;
			InetAddress mCastInet = InetAddress.getByName(mCastIp);
			
			int port = 9889;
			ServerSocket server = new ServerSocket(port);
			
			System.out.println("Servicio de Atención al Alumno Numérico (SATAN)");
			boolean fin = false;
			int id = 1;
			while (!fin) {
				// Aceptamos la conexión
				Socket socket = server.accept();
				clientesRemotos.add(socket);
				String ipCliente = socket.getRemoteSocketAddress().toString();
				System.out.println("Socket " + id + " conectado desde " + ipCliente);
				
				// Contador mesas
				ContadorMesa contadorMesa = new ContadorMesa(10);
				
				// Servidor UDP
				MCastSender serverUDP = new MCastSender(mCastInet, mCastPort);
				
				// Gestion de las mesas
				MesaService mesaService = new MesaService(id, socket, contadorMesa, serverUDP);
				
				id++;
			} // fin bucle
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
