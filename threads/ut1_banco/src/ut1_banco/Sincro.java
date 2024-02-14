package ut1_banco;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


public class Sincro {

	private int maxClientes;
	private CountDownLatch inicioConcurrenteCliente; // para el main
	private CyclicBarrier clientesListos; // para el cliente (hilo)
	
	public Sincro(int maxClientes) {
		this.maxClientes = maxClientes;
		this.inicioConcurrenteCliente = new CountDownLatch(maxClientes);
		this.clientesListos = new CyclicBarrier(maxClientes);
	}
	
	public void esperarInicioConcurrenteCliente() {
		try {
			inicioConcurrenteCliente.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void awaitClientesListos() {
		try {
			clientesListos.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	public void countFinClientes() {
		inicioConcurrenteCliente.countDown();
	}

}
