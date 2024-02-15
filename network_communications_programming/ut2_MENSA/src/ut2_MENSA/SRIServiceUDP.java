package ut2_MENSA;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SRIServiceUDP extends Thread {

	private InetAddress mCastInet;
	private int mCastPort;
	private String mensa;
	
	public SRIServiceUDP(InetAddress mCastInet, int mCastPort, String mensa) {
		this.mCastInet = mCastInet;
		this.mCastPort = mCastPort;
		this.mensa = mensa;
		
		this.start();
	}
	
	public void run() {
		try {
			// String message = flujoE.readLine();
			DatagramSocket sUDP = new DatagramSocket();
			DatagramPacket msgPantalla = new DatagramPacket(mensa.getBytes(),
					mensa.getBytes().length, mCastInet, mCastPort);
			sUDP.send(msgPantalla);
			sUDP.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mensa = "";
	}
	
}
