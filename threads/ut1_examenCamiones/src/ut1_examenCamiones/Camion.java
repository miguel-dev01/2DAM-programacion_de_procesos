package ut1_examenCamiones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Camion extends Thread {
	
	private int id;
	private Sincro sincro;
	private BufferedReader flujoE;
	private PrintWriter enviarFin; // Enviar fin a la central

	public Camion(int id, Sincro sincro, BufferedReader flujoE, PipedWriter confirmaS) {
		this.id = id;
		this.sincro = sincro;
		this.flujoE = flujoE;
		this.enviarFin = new PrintWriter(confirmaS);
		
		this.start();
	}

	@Override
	public void run() {
		sincro.iniciarCamionesConcurrentemente();
		String comandoFin;
		Random random = new Random();
		int km = 0;
		boolean fin = false;
		
		while (!fin) {
			try {
				comandoFin = flujoE.readLine();
				if (comandoFin.contains("fin")) {
					fin = true;
				} else {
					km = Integer.parseInt(comandoFin);
					System.out.println("Realizando viaje el camion " + id + ". KMs --> " + km);
				}
				Thread.sleep(random.nextInt(km));
				
				sincro.bloquearMensajeCamionACentral();
				this.enviarFin.println("fin");
				sincro.desbloquearMensajeCamionACentral();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}

}
