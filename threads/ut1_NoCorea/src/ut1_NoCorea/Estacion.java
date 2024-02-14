package ut1_NoCorea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Random;


public class Estacion extends Thread {

	private int id;
	private Sincro sincro;
	private BufferedReader flujoE;
	private PrintWriter radio; //radio flujoSalida
	private boolean fin = false;
	
	public Estacion (int id, Sincro sincro, PipedReader receptor, PipedWriter emisor) {
//		try {
			this.id = id;
			this.sincro = sincro;
			this.flujoE = new BufferedReader(receptor);
			this.radio = new PrintWriter(emisor);
			this.start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public void run() {
		sincro.notificar();
		Random random = new Random();
		
		try {
			while (!fin) {
				//sincro.bloquearRadio();
				if (random.nextInt(11) >= 8) {
					// ha habido movimiento
					sincro.bloquearRadio();
					radio.println(id);
					String comando = flujoE.readLine();
		            if (comando.contains("atacar")) {
		                System.out.println("Estacion " + id + " atacando...");
		            } else if (comando.equals("fin")) {
		            	fin = true;
		            	System.out.println("Estacion " + id + " finalizando...");
		            } else {
		            	System.out.println("Comando no reconocido en la Estacion " + id);
		            }
		            sincro.desbloquearRadio();
				} else {
					// ejecutar espera aleatoria de 0 - 1 seg
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//sincro.desbloquearRadio();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Estacion " + id + " finalizando");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
