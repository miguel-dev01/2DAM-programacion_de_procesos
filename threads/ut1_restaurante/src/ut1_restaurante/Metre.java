// METRE
package ut1_restaurante;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Metre extends Thread {
	private PipedReader receptor;
	private BufferedReader flujoE;
	private Sincro sincro;
	private boolean terminado;
	
	public Metre(PipedWriter emisor, Sincro sincro) {
		try {
			this.receptor = new PipedReader(emisor);
			this.flujoE = new BufferedReader(receptor);
			this.sincro = sincro;
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		terminado = false;
		while(!terminado) {
			try {
				String mensaje = flujoE.readLine();
				sincro.bloquearPrint();
				System.out.println("recibido al metre: " + mensaje);
				sincro.desbloquearPrint();
			} catch (IOException e) {
				finalizarMetre();
			}
		}
	}

	public void finalizarMetre() {
		terminado = true;
	}
}
