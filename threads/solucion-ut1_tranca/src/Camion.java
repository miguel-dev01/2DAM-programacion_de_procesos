import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;

public class Camion extends Thread{

	private Sincro sincro;
	private int id;
	private BufferedReader flujoE;
	private PrintWriter flujoS;
	
	public Camion(int id, BufferedReader flujoE, Sincro sincro, PipedWriter confirmaS) {
		this.id = id;
		this.flujoE = flujoE;
		this.sincro = sincro;
		this.flujoS = new PrintWriter(confirmaS);
		this.start();
	}

	public void run() {
		String comando;
		int km = 0;
		boolean fin = false;
		/* Iniciamos todos camiones concurrentemente */
		sincro.awaitInicioConcurrente();
		while (!fin) {
			try {
				comando = flujoE.readLine();
				if (comando.contains("fin")) {
					fin = true;
				} else {
					km = Integer.parseInt(comando);
					System.out.println("Camión " + id + " Recibido viaje de " + km + "km");
				}
				Thread.sleep(km);
				/* Enviamos a la central que hemos terminado bloqueando el acceso */
				sincro.lock();
				flujoS.println("fin");
				sincro.unlock();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
