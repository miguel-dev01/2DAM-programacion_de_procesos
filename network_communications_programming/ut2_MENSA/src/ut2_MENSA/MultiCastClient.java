package ut2_MENSA;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class MultiCastClient {

	public static void main(String[] args) {
		final String serverIP = "224.0.1.1";
		final int port = 7777;
		
		try {
			InetAddress mcastAddr = InetAddress.getByName(serverIP);
			InetSocketAddress group = new InetSocketAddress(mcastAddr, port);
			MulticastSocket sUDPMulti = new MulticastSocket(port);
			NetworkInterface netIf = NetworkInterface.getByName("enx00e04c36020a");
			sUDPMulti.joinGroup(group, netIf);
			System.out.println("Conectado a " + serverIP + ":" + port);
			boolean fin = false;
			byte[] buffer = new byte[1024];
		    
		    while (!fin) {
		    	DatagramPacket datagramaEntrada = new DatagramPacket(buffer, buffer.length);
		    	System.out.println("Esperando...");
		    	sUDPMulti.receive(datagramaEntrada);
		    	System.out.println(new String(datagramaEntrada.getData()));
		    }
		    sUDPMulti.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
}
