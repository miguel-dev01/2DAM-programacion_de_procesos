package ut1_05_pipes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;

public class Consumidor extends Thread {
	private BufferedReader flujoE;
	private boolean fin = false;
	public Consumidor(PipedReader receptor) {
		flujoE = new BufferedReader(receptor);
	}
	public void run() {
		while(!fin) {
			try {
				String message = flujoE.readLine();
				System.out.println("Recibido" + message);
			} catch (IOException e) {
				fin = true;
			}
		}
		System.out.println("Consumidor cerrado");
	}
}
