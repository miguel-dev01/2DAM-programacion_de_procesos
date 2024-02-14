import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Parking {
	private int maxparking;
	// semaforo sera para que no entren y salgan a la vez
	// no compartiran metodos mientras esten con uno
	private Semaphore estacionados;
	
	private Semaphore accesoContador;
	private int accesoContadorEntero;
	
	private long tiempoEstacionamientos;
	private Lock miLock = new ReentrantLock();

	public Parking(int maxparking) {
		this.maxparking = maxparking;
		this.estacionados = new Semaphore(maxparking);
	}

	public void entrarParking() {
		try {
			this.estacionados.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void salirParking() {
		this.estacionados.release();
	}
	
	public int getContadorEstacionamientos() {
		int contador = 0;
		try {
			accesoContador.acquire();
			accesoContadorEntero++;
			accesoContador.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return contador;
	}
	
	public void addTiempoEstacionamientos(long tiempoCoche) {
		miLock.lock();
		tiempoEstacionamientos += tiempoCoche;
		miLock.unlock();
	}
	public long getTiempoEstacionamientos() {
		long tiempo;
		miLock.lock();
		tiempo = tiempoEstacionamientos;
		miLock.unlock();
		return tiempo;
	}

}
