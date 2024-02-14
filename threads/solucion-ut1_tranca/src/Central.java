import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Central extends Thread {

	private int [] km ;
	private BufferedReader flujoE;
	private Sincro sincro;
	private int maxCamiones;
	private PrintWriter[] flujoS;
	
	public Central(int maxCamiones, PrintWriter[] flujoS, Sincro sincro, PipedReader confirmaE) {
		this.maxCamiones = maxCamiones;
		this.km = new int[maxCamiones];
		this.flujoE = new BufferedReader(confirmaE);
		this.flujoS = flujoS;
		this.sincro = sincro;
		this.start();
	}

	public int getKm(int i) {
		return km[i];
	}

	public void run() {
		int recorrido;
		int camion;
		String comando;
		Random aleatorio = new Random();
		
		/* Iniciamos los contadores de km*/
		for (int i = 0; i < maxCamiones; i++) {
			km[i] = 0;
		}
		
		/* Asignamos maxCamiones viajes */
		for (int i = 0; i < maxCamiones; i++) {
			recorrido = aleatorio.nextInt(500) + 500; //Cada recorrido de 500 a 1000km
			camion = aleatorio.nextInt(maxCamiones); //Elegimos un camión aleatoriamente
		    km[camion] = recorrido;
			flujoS[camion].println(recorrido);
			//Esperamos que el camión realice el viaje
			try {
				comando = flujoE.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//Despertamos al padre para que obtenga los datos de administración
		sincro.countDownEsperaFinCamiones();
		
		//Esperamos que el padre nos finalice
		sincro.awaitEsperaFinCental();
		/* Mandamos finalizar a los camiones */
		for (int i = 0; i < maxCamiones; i++) {
			flujoS[i].println("fin");
		}
		
	}
}
