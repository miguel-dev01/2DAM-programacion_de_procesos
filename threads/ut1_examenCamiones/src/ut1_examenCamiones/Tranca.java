package ut1_examenCamiones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;

public class Tranca {
	static final int MAX_CAMIONES = 10;
	
	public static void main(String[] args) throws IOException {
		
		Sincro sincro = new Sincro(MAX_CAMIONES);
		Camion[] camion = new Camion[MAX_CAMIONES];
		
		// Pipes punto a punto
		PipedWriter[] emisor = new PipedWriter[MAX_CAMIONES];
		PipedReader[] receptor = new PipedReader[MAX_CAMIONES];
		PrintWriter[] flujoS = new PrintWriter[MAX_CAMIONES];
		BufferedReader[] flujoE = new BufferedReader[MAX_CAMIONES];
		
		// Pipe compartido
		PipedWriter confirmaS = new PipedWriter();
		PipedReader confirmaE = new PipedReader(confirmaS);
		
		System.out.println("[Miguel Sanchez Garcia] Iniciamos la simulacion...");
		for (int i = 0; i < camion.length; i++) {
			emisor[i] = new PipedWriter();
			receptor[i] = new PipedReader(emisor[i]);
			flujoS[i] = new PrintWriter(emisor[i]);
			flujoE[i] = new BufferedReader(receptor[i]);
			camion[i] = new Camion(i, sincro, flujoE[i], confirmaS);
		}
		
		Central central = new Central(MAX_CAMIONES, sincro, flujoS, confirmaE);
		
		// El padre debe esperar aqui
		sincro.awaitesperarClaseTranca();
		
		// Aqui abajo imprimiremos la informacion de la simulacion
		
		
	}

}
