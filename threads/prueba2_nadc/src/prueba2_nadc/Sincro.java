package prueba2_nadc;

import java.util.concurrent.CountDownLatch;

public class Sincro {

	private CountDownLatch misilesListos;
	private CountDownLatch esperaNorad;
	
	public Sincro(int maxMisiles) {
		this.misilesListos = new CountDownLatch(maxMisiles);
		this.esperaNorad = new CountDownLatch(1);
	}
	
	public void notificarMisilListo() {
		this.misilesListos.countDown();
	}
	
	public void esperarMisilListo() {
		try {
			this.misilesListos.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void esperarNorad() {
		try {
			this.esperaNorad.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void countDownMisilListo() {
		this.esperaNorad.countDown();
	}
	
}
