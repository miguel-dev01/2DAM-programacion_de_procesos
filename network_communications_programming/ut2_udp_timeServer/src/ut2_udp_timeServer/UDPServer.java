package ut2_udp_timeServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
	
	public static void main(String[] args) {
		final int port = 7777;
		boolean fin = false;
		
		try {
			// En UDP se usan datagramas para enviar informacion.
			// No paquetes como en TCP
			DatagramSocket sUDP = new DatagramSocket(port);
			byte[] buffer = new byte[1024];
			System.out.println("Servidor UDP funcionando...");
			while (!fin) {
				// Construimos un datagrama para recibir las peticiones
				DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
				// Leemos las peticiones
				sUDP.receive(peticion);
				
				System.out.println("Datagrama recibido desde " + peticion.getAddress() + 
						", puerto: " + peticion.getPort());
				
				// Enviamos un echo como respuesta
				DatagramPacket respuesta = new DatagramPacket(peticion.getData(), peticion.getLength(),
						peticion.getAddress(), peticion.getPort());
				sUDP.send(respuesta);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}