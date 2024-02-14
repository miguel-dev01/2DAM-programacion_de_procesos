package ut1_Almacenes;

import java.util.concurrent.Semaphore;

// Clase semaforo
public class Puerta {
	
	private Semaphore puertaAbreCierra;
	
	public Puerta() {
		this.puertaAbreCierra = new Semaphore(1);
	}
	
	public boolean intentarEntrarAlAlmacen() {
		return this.puertaAbreCierra.tryAcquire();
	}
	
	public void liberarPuerta() {
		this.puertaAbreCierra.release();
	}
	
}
