import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;

public class Tranca {
	static final int MAX_CAMIONES = 10;
	
	public static void main(String[] args) throws IOException {
		/* Creamos las variables */
		float precioKM = 0.2f;
		long inicioSimulacion;
		long finSimulacion;
		long tiempoSimulacion;
		
		Camion [] camion = new Camion[MAX_CAMIONES];
		Sincro sincro = new Sincro(MAX_CAMIONES);
		PipedWriter [] emisor = new PipedWriter[MAX_CAMIONES];
		PipedReader [] receptor = new PipedReader[MAX_CAMIONES];
		PrintWriter [] flujoS = new PrintWriter[MAX_CAMIONES];
		BufferedReader [] flujoE = new BufferedReader[MAX_CAMIONES];
		
		PipedWriter confirmaS = new PipedWriter();
		PipedReader confirmaE = new PipedReader(confirmaS);
				
		/* Inicializamos los pipes y los hilos */
		for (int i = 0; i < MAX_CAMIONES; i++) {
			emisor[i] = new PipedWriter();
			receptor[i] = new PipedReader(emisor[i]);
			flujoE[i] = new BufferedReader(receptor[i]);
			flujoS[i] = new PrintWriter(emisor[i]);
			camion[i] = new Camion(i, flujoE[i], sincro, confirmaS);
		}
		
		Central central = new Central(MAX_CAMIONES, flujoS, sincro, confirmaE);
		
		System.out.println("Antonio F. Iniciamos la simulación...");
		inicioSimulacion = System.currentTimeMillis();
		
		/* Esperamos que los camiones finalicen los viajes */ 
		sincro.awaitEsperaFinCamiones();
		
		/* Imprimimos la información de facturación */
		System.out.println("Emisión de facturas");
		for (int i = 0; i < MAX_CAMIONES; i++) {
			System.out.println("Camión "+i+" factura :"+central.getKm(i)+"km Importe > "+central.getKm(i)*precioKM+"€");
		}
		
		/* Una vez impresa la información de la factura finalizamos la central*/
		sincro.countDownEsperaFinCentral();
		
		/* Imprimimos el tiempo de la simulación */
		finSimulacion = System.currentTimeMillis();
		tiempoSimulacion = finSimulacion - inicioSimulacion;
		System.out.println("Antonio F. Tiempo de simulacion " + tiempoSimulacion);
	}

}





