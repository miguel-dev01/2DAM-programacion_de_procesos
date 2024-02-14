package ut1_NADC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

// Esta clase recogera los algoritmos de sincronizacion para los hilos(misiles)
public class Sincroniza {
	private int maxmisiles;
	private CountDownLatch misilesListos;
	private CountDownLatch lanzaMisiles;
	private CountDownLatch misilesFinalizador;
	private int aciertos;
	private int fallos;
	private Semaphore miLock = new Semaphore(1);

	public Sincroniza(int maxmisiles) {
		this.maxmisiles = maxmisiles;
		this.misilesListos = new CountDownLatch(maxmisiles);
	}

	public void esperarMisilesListos() {
		try {
			misilesListos.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void notificarMisilListos() {
		misilesListos.countDown();
	}

	public void esperarActivacion() {
		try {
			misilesListos.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void realizarActivacion() {
		lanzaMisiles.countDown();
		
	}

	public void resetearContadores() {
		this.aciertos = 0;
		this.fallos = 0;
	}

	public void contarFallo() {
		try {
			miLock.acquire();
			this.fallos++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public void contarAcierto() {
		try {
			miLock.acquire();
			this.aciertos++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public int getAciertos() {
		int aciertos = 0;
		try {
			miLock.acquire();
			aciertos = this.aciertos;
			miLock.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return aciertos;
	}
	public int getFallos () {
		int fallos;
		try {
			miLock.acquire();
			fallos = this.fallos;
			miLock.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return aciertos;
	}

	public void notificarMisilFinalizado() {
		
	}

	public void esperarMisilesFinalizados() {
		
	}
} // cierre class
