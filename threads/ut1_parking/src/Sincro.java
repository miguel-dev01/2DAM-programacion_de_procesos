import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Sincro {
	private int maxparking;
	private int simulacoches;
	private CountDownLatch esperaFinCoches;
	private CyclicBarrier inicioConcurrenteCoches;
	// todos los coches(hilos), hasta que el ultimo no este listo
	// no empiezan a entrar al parking --> esto se consigue con CyclicBarrier

	public Sincro(int maxparking, int simulacoches) {
		this.maxparking = maxparking;
		this.simulacoches = simulacoches;
		this.esperaFinCoches = new CountDownLatch(simulacoches);
		this.inicioConcurrenteCoches = new CyclicBarrier(simulacoches);
	}
	
	public void esperarFinCoches() {
		try {
			esperaFinCoches.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void notificarFinCoches() {
		esperaFinCoches.countDown();
	}
	
	public void esperarInicioConcurrenteCoches() {
		try {
			inicioConcurrenteCoches.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
}
