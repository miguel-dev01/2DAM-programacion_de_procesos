package ut1_filosofos;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Filosofo extends Thread {
	
	private static final int MAX_COMIDAS = 3;
	private int id;
	private Semaphore[] palilloEspera;
	private CyclicBarrier inicio;
	int palilloIzquierda;
	int palilloDerecha;
	private int comidas = 0;

	public Filosofo(int id, Semaphore[] palilloEspera, int[][] palillos, CyclicBarrier inicio) {
		this.id = id;
		this.palilloEspera = palilloEspera;
		this.palilloDerecha = palillos[id][0];
		this.palilloIzquierda = palillos[id][1];
		this.inicio = inicio;
		this.start();
	}
	
	public void run() {
		try {
			inicio.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		while(comidas < MAX_COMIDAS) {
			pensar();
			comer();
			comidas++;
		}
		System.out.println("Filosofo " + id + " ha terminado");
	}

	private void comer() {
		if ((palilloEspera[palilloIzquierda].tryAcquire()) && 
				(palilloEspera[palilloDerecha].tryAcquire())) {
			System.out.println("Filosofo " + id + " coge los palillos " + 
				palilloIzquierda + " y " + palilloDerecha);
			System.out.println("Filosofo " + id + " comiendo");
			palilloEspera[palilloIzquierda].release();
			palilloEspera[palilloDerecha].release();
			
		} else if ((palilloEspera[palilloIzquierda].tryAcquire()) == true) {
			System.out.println("Filosofo " + id + " coge el palillo " + palilloIzquierda);
			palilloEspera[palilloIzquierda].release();
		} else if (palilloEspera[palilloDerecha].tryAcquire()) {
			System.out.println("Filosofo " + id + " coge el palillo " + palilloDerecha);
			palilloEspera[palilloDerecha].release();
		}
	}

	private void pensar() {
		Random aleatorio = new Random();
		int espera = aleatorio.nextInt(1000) + 500;
		try {
			System.out.println("Filosofo " + id + " pensando...");
			Thread.sleep(espera);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
