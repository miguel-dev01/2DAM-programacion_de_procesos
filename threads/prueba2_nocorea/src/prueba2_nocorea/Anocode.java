package prueba2_nocorea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Anocode {
	static final int MAX_MISILES = 10;
	
	public static void main(String[] args) throws IOException {
		
		Scanner teclado = new Scanner(System.in);
		Sincro sincro = new Sincro(MAX_MISILES);
		Estacion[] estacion = new Estacion[MAX_MISILES];
		
		// Pipes dedicados para cada una de las estaciones
		PipedWriter emisor[] = new PipedWriter[MAX_MISILES];
		PipedReader receptor[] = new PipedReader[MAX_MISILES];
		PrintWriter flujoSAtacar[] = new PrintWriter[MAX_MISILES];
		
		// Mismo pipe para que las estaciones puedan comunicar a la central
		PipedWriter emisorRadio = new PipedWriter();
		PipedReader receptorRadio = new PipedReader(emisorRadio);
		BufferedReader flujoERadio = new BufferedReader(receptorRadio);
		
		System.out.println("Iniciando el servicio de escucha de misiles");
		for (int i = 0; i < MAX_MISILES; i++) {
			emisor[i] = new PipedWriter();
			receptor[i] = new PipedReader(emisor[i]);
			flujoSAtacar[i] = new PrintWriter(emisor[i]);
			estacion[i] = new Estacion(i, sincro, emisorRadio, receptor[i]);
		}
		
		// Esperamos que esten todas las estaciones operativas
		sincro.awaitEsperarEstacionesOperativas();
		System.out.println("Todas las estaciones operativas");
		
		boolean fin = false;
		while (!fin) {
			String idEstacion = flujoERadio.readLine();
			System.out.println("Estacion Nº " + idEstacion + " ha detectado movimiento. Envia el comando para atacar: ");
			
			int id = Integer.parseInt(idEstacion);
			String comandoAtacar = teclado.nextLine();
			if (comandoAtacar.equals("atacar")) {
				flujoSAtacar[id].println("atacar");
				flujoSAtacar[id].flush();
			} else if (comandoAtacar.equals("fin")) {
				for (int i = 0; i < MAX_MISILES; i++) {
					flujoSAtacar[i].println("fin");
					flujoSAtacar[i].flush();
				}
				fin = true;
			} else {
				System.out.println("La estacion " + id + " ha abortado.");
				flujoSAtacar[id].println(comandoAtacar);
				flujoSAtacar[id].flush();
			}
			
		}
		
		
	}

}
