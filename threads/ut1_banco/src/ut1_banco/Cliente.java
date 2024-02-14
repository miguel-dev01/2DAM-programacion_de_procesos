package ut1_banco;

import java.util.Random;

public class Cliente extends Thread {
	
	private int id;
	private Cuenta cuenta;
	private Sincro sincro;
	private Operacion operacion;
	private double saldo;

	public Cliente(int id, Cuenta cuenta, Sincro sincro, Operacion operacion, double saldo) {
		this.id = id;
		this.cuenta = cuenta;
		this.sincro = sincro;
		this.saldo = saldo;
		this.start();
	}
	
	public void run() {
		// Iniciamos los hilos concurrentemente, cuando todos lleguen, arrancaran todos a la vez
		// pero iniciados de forma concurrente.
		// usaremos CyclicBarrier
		sincro.awaitClientesListos();
		
		Random aleatorio = new Random();
		try {
			Thread.sleep(aleatorio.nextInt(500) + 500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Cliente " + id + " operando...");
		// una vez termina la espera, realizamos la operacion // se puede hacer tambien con un switch
		if (operacion == Operacion.REINTEGRO) {
			System.out.println("Cliente " + id + ", reintegra " + saldo);
			this.cuenta.reintegrar(saldo);
		} else if (operacion == Operacion.INGRESO) {
			System.out.println("Cliente " + id + ", ingresa " + saldo);
			this.cuenta.ingresar(saldo);
		}
		
		// comunicamos al padre que hemos terminado
		sincro.countFinClientes();
	}

}
