package ut2_MUGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MultiCastEmisor extends Thread {

	private InetAddress mCastInet;
	private int mCastPort;
	private BufferedReader flujoE;
	
	public MultiCastEmisor(InetAddress mCastInet, int mCastPort, PipedReader receptor) {
		this.mCastInet = mCastInet;
		this.mCastPort = mCastPort;
		this.flujoE = new BufferedReader(receptor);
		
		this.start();
	}
	
	public void run() {
		try {
			String message = flujoE.readLine();
			DatagramSocket sUDP = new DatagramSocket();
			DatagramPacket msgPantalla = new DatagramPacket(message.getBytes(),
					message.getBytes().length, mCastInet, mCastPort);
			sUDP.send(msgPantalla);
			sUDP.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
