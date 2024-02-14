package Domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Nodo extends Thread {
	
	private int id;
	private Sincro sincro;
	private PrintWriter flujoS;
	private BufferedReader flujoE;

	public Nodo(int id, Sincro sincro, PipedWriter tiempoS, BufferedReader flujoE) {
		this.id = id;
		this.sincro = sincro;
		this.flujoS = new PrintWriter(tiempoS);
		this.flujoE = flujoE;
		this.start();
	}
	
	@Override
	public void run() {
		try {
			sincro.awaitNodosInicilizados();
			
			sincro.awaitDespertarNodos();
			
			String cantidadNumeros = flujoE.readLine();
			int numeros = Integer.parseInt(cantidadNumeros);
			long inicioSimulacion = System.currentTimeMillis();
			for (int i = 0; i < 3; i++) {
				System.out.println("Nodo " + id + " calculando " + cantidadNumeros);
				generatePrimes(numeros);
				if (cantidadNumeros.contains("fin")) {
					break;
				}
			}
			long finSimulacion = System.currentTimeMillis();
			long tiempoSimulacion = finSimulacion - inicioSimulacion;
			flujoS.println(tiempoSimulacion); // enviamos tiempo de simulacion
			sincro.countDownDespertarNodos();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Integer> generatePrimes(int numberOfPrimes) {
		List<Integer> primes = new ArrayList<>();
		int number = 2; // El primer número primo
		while (primes.size() < numberOfPrimes) {
			if (isPrime(number)) {
				primes.add(number);
			}
			number++;
		}
		return primes;
	}
	private static boolean isPrime(int number) {
		for (int i = 2; i <= Math.sqrt(number); i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}
	
}
