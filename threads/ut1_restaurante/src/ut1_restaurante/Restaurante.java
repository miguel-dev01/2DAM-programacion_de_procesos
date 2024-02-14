package ut1_restaurante;

import java.io.PipedWriter;
import java.util.concurrent.Semaphore;

@SuppressWarnings("unused")
public class Restaurante {
	private final static int MAX_COCINEROS = 8;
	
	public static void main(String[] args) {
		final int max_Mesas = 20;
		Sincro sincro = new Sincro(MAX_COCINEROS, max_Mesas);
		PipedWriter flujoE = new PipedWriter();
		Mesa[] mesa = new Mesa[max_Mesas];
		Metre metre = new Metre(flujoE, sincro);
		
		System.out.println("Alumno: Miguel Sanchez Garcia, Hora: 13:37");
		System.out.println("Iniciando el servicio del restaurante...");
		for (int i = 0; i < mesa.length; i++) {
			mesa[i] = new Mesa(i, metre, sincro);
		}
		
		System.out.println("fin...");
		metre.finalizarMetre();
		sincro.esperarComensalesComidos();
	}
}
