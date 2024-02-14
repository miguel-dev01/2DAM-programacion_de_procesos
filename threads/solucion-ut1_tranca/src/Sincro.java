import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Sincro {

    CountDownLatch esperaFinCentral;
    CountDownLatch esperaFinCamiones;
    CyclicBarrier inicioConcurrente;
    Semaphore lock;
    
	public Sincro(int maxCamiones) {
		this.esperaFinCentral = new CountDownLatch(1);
		this.esperaFinCamiones = new CountDownLatch(1);
		this.inicioConcurrente = new CyclicBarrier(maxCamiones);
		this.lock = new Semaphore(1);
	}

	public void awaitEsperaFinCental() {
		try {
			this.esperaFinCentral.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void awaitEsperaFinCamiones() {
		try {
			this.esperaFinCamiones.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void countDownEsperaFinCentral() {
		this.esperaFinCentral.countDown();
	}

	public void countDownEsperaFinCamiones() {
		this.esperaFinCamiones.countDown();
	}
	public void awaitInicioConcurrente() {
		try {
			this.inicioConcurrente.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public void lock() {
		try {
			this.lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void unlock() {
		this.lock.release();
	}

	
}
