package ut1_Almacenes;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Sincro {

	private int clientes;
	private CyclicBarrier clientesListos; // para los hilos
	private CountDownLatch esperaClientes; // para el main
	
	public Sincro(int clientes) {
		this.clientes = clientes;
		this.clientesListos = new CyclicBarrier(clientes);
		this.esperaClientes = new CountDownLatch(clientes);
	}

	public void notificarClientesListos() {
		try {
			this.clientesListos.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	public void esperarClientesListos() {
		try {
			this.esperaClientes.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void finalizarClientesListos() {
		this.esperaClientes.countDown();
	}

}
