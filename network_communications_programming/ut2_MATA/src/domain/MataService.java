package domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MataService extends Thread {

	private int id;
	private PrintWriter flujoS;
	private Scanner flujoE;

	public MataService(int id, Socket socket) throws IOException {
		this.id = id;
		this.flujoS = new PrintWriter(socket.getOutputStream());
		this.flujoE = new Scanner(socket.getInputStream());

		this.start();
	}

	public void run() {
		flujoS.println("Bienvenido al servicio de mensajeria MATA");
		flujoS.println("Cliente " + id + " conectado correctamente.");
		flujoS.flush();
		boolean fin = false;
		MataUDPReceiver receiverUDP = null;
		while (!fin) {
			String comando = flujoE.nextLine();
			
			if (comando.equals("START")) {
				try {
					receiverUDP = new MataUDPReceiver(id, flujoS);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			} else if (comando.equals("STOP")) {
				receiverUDP.finalizarMataUDPReceiver();
			} else if (comando.equals("QUIT")) {
				MataServer.finalizarMataServer();
				fin = true;
				flujoS.println("Te has desconectado del servicio");
				flujoS.flush();
			} else {
				flujoS.println("Comando no reconocido");
				flujoS.flush();
			}
			
		} // cierre While
		
	} // cierre run()
	
}
