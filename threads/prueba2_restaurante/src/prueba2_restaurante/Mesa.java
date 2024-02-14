package prueba2_restaurante;

import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Mesa extends Thread {

	private int id;
	private Sincro sincro;
	private PrintWriter flujoS;
	
	public Mesa(int id, Sincro sincro, PipedWriter emisor) {
		this.id = id;
		this.sincro = sincro;
		this.flujoS = new PrintWriter(emisor);
		this.start();
	}
	
	@Override
	public void run() {
		Random random = new Random();
		boolean sinComanda = false;
		
		sincro.esperarInicioConcMesas();
		while(!sinComanda) {
			try {
				if (sincro.intentarObtenerCocinero()) {
					Thread.sleep(random.nextInt(2));
					System.out.println("Mesa " + id + " obtiene cocinero y termina de comer");
					sincro.soltarCocineroDeLaMesa();
					sinComanda = true;
				} else {
					Thread.sleep(random.nextInt(1));
					sincro.bloquearPantalla();
					flujoS.println("Mesa " + id + " esperando cocinero...");
					sincro.desbloquarPantalla();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		sincro.esperarMesasAtendidasCountDown();
	}
	
}
