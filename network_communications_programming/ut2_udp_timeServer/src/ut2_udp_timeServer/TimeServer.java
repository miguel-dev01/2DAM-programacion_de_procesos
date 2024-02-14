package ut2_udp_timeServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeServer {
	
	public static void main(String[] args) {
		try {
			final String serverIP = "224.0.0.1";
			final int port = 7777;
			final int delay = 1000;
			boolean end = false;
		
			int hh, mm, ss;
			InetAddress inetServer = InetAddress.getByName(serverIP);
			
			DatagramSocket sUDP = new DatagramSocket();
			String message;
			while (!end) {
				var calendar = new GregorianCalendar();
				hh = calendar.get(Calendar.HOUR_OF_DAY);
				mm = calendar.get(Calendar.MINUTE);
				ss = calendar.get(Calendar.SECOND);
				message = "Clock >" + hh + ":" + mm + ":" + ss;
				DatagramPacket timeMessage = 
						new DatagramPacket(message.getBytes(), message.getBytes().length, inetServer, port);
				sUDP.send(timeMessage);
				System.out.println("Sending..." + message);
				Thread.sleep(delay);
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
