package Domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;

public class Coma extends Thread {
	
	private int numNodos;
	
	public Coma(int numNodos) {
		this.numNodos = numNodos;
		this.start();
	}

	@Override
	public void run() {
		try {
			Sincro sincro = new Sincro(numNodos);
			Nodo[] nodo = new Nodo[numNodos];
			// Pipe punto a punto para que Coma envie cantidad de primos a computar o fin, a los Nodos
			PipedWriter[] emisor = new PipedWriter[numNodos];
			PipedReader[] receptor = new PipedReader[numNodos];
			PrintWriter[] flujoEnviarNumeros = new PrintWriter[numNodos];
			BufferedReader[] flujoE = new BufferedReader[numNodos];
			// Pipe unico y compartido para que el Nodo se comunique con Coma
			PipedWriter tiempoS = new PipedWriter();
			PipedReader tiempoE = new PipedReader(tiempoS);
			BufferedReader flujoETiempo = new BufferedReader(tiempoE);
			
			System.out.println("[Miguel Sanchez Garcia] Iniciando la simulacion de nodos...");
			for (int i = 0; i < numNodos; i++) {
				emisor[i] = new PipedWriter();
				receptor[i] = new PipedReader(emisor[i]);
				flujoEnviarNumeros[i] = new PrintWriter(emisor[i]);
				flujoE[i] = new BufferedReader(receptor[i]);
				nodo[i] = new Nodo(i, sincro, tiempoS, flujoE[i]);
			}
			
			sincro.countDownDespertarNodos();
			System.out.println("Todos los nodos encendidos e inicializados");
			
			System.out.println("Lanzamos la ronda 1");
			Thread.sleep(1);
			long inicioSimulacion = System.currentTimeMillis();
			for (int i = 0; i < numNodos; i++) {
				flujoEnviarNumeros[i].println(100);
//				for (int j = 0; j < numNodos; j++) {
//					System.out.println("Lanzamos la ronda " + i);
//					flujoEnviarNumeros[i].println(200);
//				}
			}
			sincro.awaitDespertarNodos();
			
			for (int i = 0; i < numNodos; i++) {
				flujoEnviarNumeros[i].println("fin");
			}
			long finSimulacion = System.currentTimeMillis();
			
			String tiempoSimulaNodo = flujoETiempo.readLine();
			System.out.println("La nodos han tardado " + tiempoSimulaNodo);
			
			long tiempoSimulacion = finSimulacion - inicioSimulacion;
			System.out.println("La simulacion ha tardado " + tiempoSimulacion);
			
			flujoETiempo.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
