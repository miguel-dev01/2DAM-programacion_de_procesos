package prueba2_restaurante;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Restaurante {
	final static int MAX_COCINEROS = 8;

	public static void main(String[] args) throws IOException {
		final int maxMesas = 20;
		
		PipedWriter emisor = new PipedWriter();
		PipedReader receptor = new PipedReader(emisor);
		Sincro sincro = new Sincro(maxMesas, MAX_COCINEROS);
		Mesa[] mesa = new Mesa[maxMesas];
		Metre metre = new Metre(sincro, receptor);
		
		System.out.println("Iniciando servicio del restaurante...");
		for (int i = 0; i < maxMesas; i++) {
			mesa[i] = new Mesa(i, sincro, emisor);
		}
		
		// El padre esperara a que las mesas terminen de ser atendidas
		sincro.esperarMesasAtendidas();
		
		metre.finalizar();
		
		System.out.println("Finalizando servicio del restaurante");
		System.out.println("Tiempo de simulacion: ");
	}

}
