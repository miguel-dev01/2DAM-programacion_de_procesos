package domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public class MataUDPReceiver extends Thread {

	private int idCliente;
	private InetAddress mCastInet;
	private int mCastPort;
	private PrintWriter flujoS;
	private boolean fin;
	
	public MataUDPReceiver(int id, PrintWriter flujoS) throws UnknownHostException {
		this.mCastInet = InetAddress.getByName("224.0.23.4");
		this.mCastPort = 8823;
		this.idCliente = id;
		this.flujoS = flujoS;
		this.fin = false;
		
		this.start();
	}
	
	public void run() {
		flujoS.println("Cliente " + idCliente + " subscrito a los mensajes de la red 224.0.23.4:8823");
		flujoS.flush();
		
		try {
			InetSocketAddress group = new InetSocketAddress(mCastInet, mCastPort);
			MulticastSocket sUDPMulti = new MulticastSocket(mCastPort);
			NetworkInterface netIf = NetworkInterface.getByName("enx00e04c36020a");
			sUDPMulti.joinGroup(group, netIf);
			// System.out.println("Conectado a " + serverIP + ":" + port);
			byte[] buffer = new byte[1024];
		    
		    while (!fin) {
		    	DatagramPacket recibido = new DatagramPacket(buffer, buffer.length);
		    	sUDPMulti.receive(recibido);
		    	flujoS.println(new String(recibido.getData()));
		    	flujoS.flush();
		    }
		    
		    sUDPMulti.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // cierre run
	
	public void finalizarMataUDPReceiver() {
		flujoS.println("Cliente " + idCliente + " NO subscrito a los mensajes de la red 224.0.23.4:8823");
		flujoS.flush();
		this.fin = true;
		
	}
	
}
