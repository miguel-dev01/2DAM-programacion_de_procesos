package ut1_05_pipes;

import java.io.PipedReader;
import java.io.PipedWriter;

public class Main {

	public static void main(String[] args) {
		// Creamos la tuberia
		PipedWriter emisor = new PipedWriter();
		PipedReader receptor = new PipedReader();
		// Creamos productor y consumidor
		Productor pHilo = new Productor(emisor);
		Consumidor cHilo = new Consumidor(receptor);
		pHilo.start();
		cHilo.start();
	}

}
