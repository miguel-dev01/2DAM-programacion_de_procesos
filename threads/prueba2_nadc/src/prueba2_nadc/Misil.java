package prueba2_nadc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Misil extends Thread {
	
	private int id;
	private Sincro sincro;
	private PipedReader receptor;
	private BufferedReader flujoE;
	
	public Misil(int id, Sincro sincro, PipedWriter emisor) throws IOException {
		this.id = id;
		this.sincro = sincro;
		this.receptor = new PipedReader(emisor);
		this.flujoE = new BufferedReader(receptor);
		this.start();
	}
	
	public void run() {
		try {
			//Informa a NORAD que esta listo
			System.out.println("Misil " + id + " armado");
			sincro.notificarMisilListo();
			//Esperamos que el NORAD nos active
			sincro.countDownMisilListo();
			System.out.println("Misil " + id + " esperando doble verificacion...");
			
			String comandoAtaca = flujoE.readLine();
			if (comandoAtaca.contains("ataca")) {
				System.out.println("Misil " + id + " se ha disparado");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
