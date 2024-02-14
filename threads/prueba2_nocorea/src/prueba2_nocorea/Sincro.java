package prueba2_nocorea;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Sincro {

	private CountDownLatch esperarEstacionesOperativas;
	private Semaphore print;
	
	public Sincro(int maxEstaciones) {
		this.esperarEstacionesOperativas = new CountDownLatch(maxEstaciones);
		this.print = new Semaphore(1);
		
	}
	
	public void awaitEsperarEstacionesOperativas() {
		try {
			this.esperarEstacionesOperativas.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void bloquearPrint() {
		try {
			this.print.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void desbloquearPrint() {
		this.print.release();
	}

	public void countDownEsperarEstacionesOperativas() {
		this.esperarEstacionesOperativas.countDown();
	}
}
