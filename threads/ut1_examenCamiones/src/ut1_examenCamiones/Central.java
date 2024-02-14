package ut1_examenCamiones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PrintWriter;
import java.util.Random;

public class Central extends Thread {

	private int maxCamiones;
	private int[] km;
	private Sincro sincro;
	private PrintWriter[] flujoSCamion;
	private BufferedReader flujoE;
	
	public Central(int MAX_CAMIONES, Sincro sincro, PrintWriter[] flujoS, PipedReader confirmaE) {
		this.flujoSCamion = flujoS;
		this.flujoE = new BufferedReader(confirmaE);
		this.maxCamiones = MAX_CAMIONES;
		this.km = new int[MAX_CAMIONES];
		this.start();
	}

	@Override
	public void run() {
		Random random = new Random();
		int camion;
		int recorrido;
		String comandoFin;
		
//		for (int i = 0; i < maxCamiones; i++) {
//			km[i] = 0;
//		}
		for (int i = 0; i < maxCamiones; i++) {
			camion = random.nextInt(10);
			recorrido = random.nextInt(500) + 500;
			km[camion] = recorrido;
			flujoSCamion[camion].println(recorrido);
			try {
				comandoFin = flujoE.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Despertar al padre para enviarle la informacion
		sincro.countDownTranca();
		
		// Esperamos que el padre nos finalice
		sincro.awaitesperarClaseCentral();
		/* Mandamos finalizar a los camiones */
		for (int i = 0; i < maxCamiones; i++) {
			flujoSCamion[i].println("fin");
		}
		
	}
}
