package ut1_NoCorea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Scanner;


public class Anocode {
	final static int MAX_ESTACIONES = 5;
	// boolean fin = false;
	public static void main(String[] args) {
		try {
			Estacion[] estacion = new Estacion[MAX_ESTACIONES];
			Sincro sincro = new Sincro(MAX_ESTACIONES);
			
			PipedWriter pipeSalida = new PipedWriter(); // la radio
			PipedReader pipeEntrada = new PipedReader(pipeSalida); // la radio
			PrintWriter[] flujoSAtacar = new PrintWriter[MAX_ESTACIONES];
			
			PipedWriter[] radioE = new PipedWriter[MAX_ESTACIONES];
			BufferedReader flujoE = new BufferedReader(pipeEntrada);
			
			Scanner teclado = new Scanner(System.in);
			System.out.println("Alumno: Miguel Sanchez Garcia, Dia: 22/10/2023");
			System.out.println("Iniciando el servicio antiescucha de las estaciones...");
			for (int i = 0; i < estacion.length; i++) {
				radioE[i] = new PipedWriter();
				flujoSAtacar[i] = new PrintWriter(radioE[i]);
				// El pipeSalida, es en realidad un PipedWriter en el que nos llegara a la central
				// informacion de la estacion
				estacion[i] = new Estacion(i, sincro, new PipedReader(radioE[i]), pipeSalida);
			}
			sincro.esperarInicioConcurrenteEstaciones(); // esperamos a que todas las estaciones esten listas
			System.out.println("Todas las estaciones estan operativas...");
			
			while (true) {
				String escucha = flujoE.readLine();
				int idEstacion = Integer.parseInt(escucha);
				System.out.println("Nº Estacion " + idEstacion + " ha detectado movimiento...");
				
				System.out.println("Introduce una orden: ");
				String comando = teclado.nextLine();
				
				if (comando.equals("atacar")) {
					flujoSAtacar[idEstacion].println("atacar");
					flujoSAtacar[idEstacion].flush();
				} else {
					System.out.println("La estacion " + idEstacion + " ha abortado.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}