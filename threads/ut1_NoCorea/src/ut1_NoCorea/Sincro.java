package ut1_NoCorea;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;


public class Sincro {
	
	private int maxEstaciones;
	private CountDownLatch inicioConcurrenteEstaciones;
	private Semaphore print;
	
	public Sincro(int maxEstaciones) {
		this.maxEstaciones = maxEstaciones;
		this.inicioConcurrenteEstaciones = new CountDownLatch(maxEstaciones);
		this.print = new Semaphore(maxEstaciones);
	}
	
	public void esperarInicioConcurrenteEstaciones() {
		try {
			inicioConcurrenteEstaciones.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void notificar() {
		// notifica estacion por estacion que van estando listas
		inicioConcurrenteEstaciones.countDown();
	}
	
	public void bloquearRadio() {
		try {
			print.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void desbloquearRadio() {
		print.release();
	}
}
