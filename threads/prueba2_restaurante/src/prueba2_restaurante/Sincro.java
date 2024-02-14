package prueba2_restaurante;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Sincro {
	
	private CountDownLatch esperarMesasAtendidas;
	private CyclicBarrier esperarMesasInicioConcurrente;
	private Semaphore cocineros;
	private Semaphore bloquearPantalla;
	
	public Sincro(int maxMesas, int maxCocineros) {
		this.esperarMesasAtendidas = new CountDownLatch(maxMesas);
		this.esperarMesasInicioConcurrente = new CyclicBarrier(maxMesas);
		this.cocineros = new Semaphore(maxCocineros);
		this.bloquearPantalla = new Semaphore(1);
	}
	
	public void esperarMesasAtendidas() {
		try {
			this.esperarMesasAtendidas.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void esperarMesasAtendidasCountDown() {
		this.esperarMesasAtendidas.countDown();
	}
	
	public void esperarInicioConcMesas() {
		try {
			this.esperarMesasInicioConcurrente.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public boolean intentarObtenerCocinero() {
		return this.cocineros.tryAcquire();
	}
	
	public void soltarCocineroDeLaMesa() {
		this.cocineros.release();
	}
	
	public void bloquearPantalla() {
		try {
			this.bloquearPantalla.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void desbloquarPantalla() {
		this.bloquearPantalla.release();
	}
	
}
