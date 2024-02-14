package main;

import domain.Buffer;
import domain.Consumidor;
import domain.Productor;

public class ProductorConsumider {

	public static void main(String[] args) {
		/*
		 * productor.join(); y consumidor.join();: Estas líneas de código se utilizan 
		 * para esperar a que los hilos "productor" y "consumidor" (metodos start()) terminen su 
		 * ejecución antes de que el programa principal continúe. La llamada al método join() bloqueará 
		 * la ejecución del hilo principal hasta que el hilo al que se llama termine su trabajo.
		 */
		Buffer miBuffer = new Buffer();
		Productor productor = new Productor(miBuffer);
		Consumidor consumidor = new Consumidor(miBuffer);
		System.out.println("Iniciamos hilos");
		productor.start();
		consumidor.start();
		try {
			// Espera hasta que el productor termine
			productor.join();
			// Espera hasta el consumdor termine
			consumidor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Productor-Consumidor Finalizado");
	}

}
