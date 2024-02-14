package Domain;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Sincro {

	private CyclicBarrier esperarNodosALaVez;
	private CountDownLatch despertarNodos; // desde el padre manda a despertar a los nodos
	private CountDownLatch despertarComa;
	
	public Sincro(int numNodos) {
		this.esperarNodosALaVez = new CyclicBarrier(numNodos);
		this.despertarNodos = new CountDownLatch(1);
		this.despertarComa = new CountDownLatch(1);
	}

	public void awaitNodosInicilizados() {
		try {
			this.esperarNodosALaVez.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public void countDownDespertarNodos() {
		this.despertarNodos.countDown();
	}
	
	public void awaitDespertarNodos() {
		try {
			this.despertarNodos.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void awaitDespertarComa() {
		try {
			this.despertarComa.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void countDownDespertarComa() {
		this.despertarComa.countDown();
	}
	
}
