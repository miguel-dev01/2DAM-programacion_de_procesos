// CLASE CON LOS MECANISMOS DE SINCRONIZACION
package ut1_restaurante;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Sincro {
	private CyclicBarrier inicioConcurrenteMesas;
	private CountDownLatch comensalesComidos;
	private Semaphore cocinero;
	private Semaphore print;
	
	public Sincro(int maxCocineros, int numMesas) {
		this.inicioConcurrenteMesas = new CyclicBarrier(maxCocineros);
		this.comensalesComidos = new CountDownLatch(numMesas);
		this.cocinero = new Semaphore(maxCocineros);
		this.print = new Semaphore(1);
	}
	
	public void esperarInicioConcurrenteMesas() {
		try {
			inicioConcurrenteMesas.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public void notificarComensalesComidos() {
		comensalesComidos.countDown();
	}
	
	public void esperarComensalesComidos() {
		try {
			comensalesComidos.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean intetoObtenerCocinero() {
		return cocinero.tryAcquire();
	}
	
	public void liberarCocinero() {
		cocinero.release();
	}
	
	// Los siguientes dos metodos, hacen que los hilos 
	// no impriman a la vez por pantalla.
	public void bloquearPrint() {
		try {
			print.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void desbloquearPrint() {
		print.release();
	}
}
