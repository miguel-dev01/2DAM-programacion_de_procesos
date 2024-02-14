package prueba2_restaurante;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;

public class Metre extends Thread {

	private Sincro sincro;
	private BufferedReader flujoE;
	private boolean fin;
	
	public Metre(Sincro sincro, PipedReader receptor) {
		this.sincro = sincro;
		this.flujoE = new BufferedReader(receptor);
		this.start();
	}
	
	@Override
	public void run() {
		fin = false;
		while(!fin) {
			try {
				String mensaje = flujoE.readLine();
				sincro.bloquearPantalla();
				System.out.println("Recibido: " + mensaje);
				sincro.desbloquarPantalla();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void finalizar() {
		this.fin = true;
	}

}
