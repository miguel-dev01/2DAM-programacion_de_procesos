package prueba2_nocorea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Estacion extends Thread {

	private int id;
	private Sincro sincro;
	private PrintWriter flujoS;
	private BufferedReader flujoE;
	private boolean fin = false;
	
	public Estacion(int id, Sincro sincro, PipedWriter emisorRadio, PipedReader receptor) {
		this.id = id;
		this.sincro = sincro;
		// Pipe unico para enviar a la central
		this.flujoS = new PrintWriter(emisorRadio);
		// Pipe de cada una de las estaciones para leer lo que viene de la central
		this.flujoE = new BufferedReader(receptor);
		this.start();
	}
	
	@Override
	public void run() {
		try {
			sincro.countDownEsperarEstacionesOperativas();
			Random random = new Random();
			int movimiento = random.nextInt(10);
			while(!fin) {
				if (movimiento >= 8) {
					sincro.bloquearPrint();
					flujoS.println(id);
					sincro.desbloquearPrint();
				} else {
					Thread.sleep(random.nextInt(1) + 1);
				}
				
				String comandoAtaca = flujoE.readLine();
				if (comandoAtaca.contains("ataca")) {
					System.out.println("Estacion Nº " + id + " atacando...");
				} else if (comandoAtaca.contains("fin")) {
					System.out.println("Estacion Nº " + id + " abortando y cerrando...");
					fin = true;
				} else {
					System.out.println("Estacion Nº " + id + " no ha reconocido el comando.");
				}
			}
		
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
