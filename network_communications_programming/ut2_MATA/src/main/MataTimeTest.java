package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MataTimeTest {
	
	public static void main(String[] args) {
		try {
			final String serverIP = "224.0.23.4";
			final int port = 8823;
			final int delay = 1000;
			boolean end = false;
		
			int hh, mm, ss;
			InetAddress inetServer = InetAddress.getByName(serverIP);
			
			DatagramSocket sUDP = new DatagramSocket();
			String message;
			int numMensa = 0;
			while (!end) {
				if (numMensa == 100) {
					numMensa = 0;
				}
				var calendar = new GregorianCalendar();
				hh = calendar.get(Calendar.HOUR_OF_DAY);
				mm = calendar.get(Calendar.MINUTE);
				ss = calendar.get(Calendar.SECOND);
				message = "Mensaje " + numMensa + " > " + hh + ":" + mm + ":" + ss;
				DatagramPacket timeMessage = 
						new DatagramPacket(message.getBytes(), message.getBytes().length, inetServer, port);
				sUDP.send(timeMessage);
				System.out.println(message);
				Thread.sleep(delay);
				numMensa++;
			}
			
			sUDP.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
