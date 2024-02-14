package ut1_examenCamiones;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Sincro {

	private CountDownLatch esperarClaseTranca;
	private CountDownLatch esperarClaseCentral;
	private CyclicBarrier inicioCamionesConc;
	private Semaphore lockMensajeCamion;
	
	public Sincro(int maxCamiones) {
		this.esperarClaseTranca = new CountDownLatch(1);
		this.esperarClaseCentral = new CountDownLatch(1);
		this.inicioCamionesConc = new CyclicBarrier(maxCamiones);
		this.lockMensajeCamion = new Semaphore(1);
	}
	
	public void awaitesperarClaseCentral( ) {
		try {
			this.esperarClaseCentral.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void awaitesperarClaseTranca( ) {
		try {
			this.esperarClaseTranca.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void countEsperarClaseTranca() {
		this.esperarClaseTranca.countDown();
	}

	public void iniciarCamionesConcurrentemente() {
		try {
			this.inicioCamionesConc.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public void bloquearMensajeCamionACentral() {
		try {
			this.lockMensajeCamion.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void desbloquearMensajeCamionACentral() {
		this.lockMensajeCamion.release();
	}

	public void countDownTranca() {
		this.esperarClaseTranca.countDown();
	}
}
