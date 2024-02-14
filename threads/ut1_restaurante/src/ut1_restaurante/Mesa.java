// CONJUNTO DE CLIENTES
package ut1_restaurante;

import java.util.Random;

@SuppressWarnings("unused")
public class Mesa extends Thread {
	
	private int id;
	private Sincro sincro;
	private Metre metre;
	boolean sinComanda = false;
	
	public Mesa(int id, Metre metre, Sincro sincro) {
		this.id = id;
		this.metre = metre;
		this.sincro = sincro;
		this.start();
	}
	
	public void run() {
		Random aleatorio = new Random();
		sincro.esperarInicioConcurrenteMesas();

		while (!sinComanda) {
	        if (sincro.intetoObtenerCocinero()) {
	            try {
	            	Thread.sleep(aleatorio.nextInt(2000));
	                print("Mesa " + id + " obtiene cocinero...");
	                sinComanda = true;
	                print("Mesa " + id + " termina de comer...");
	                sincro.liberarCocinero();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        } else {
	            try {
	                Thread.sleep(aleatorio.nextInt(1000));
	                print("Mesa " + id + " esperando cocinero...");
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
		}
		sincro.notificarComensalesComidos();
	}
	
	private void print(String mesa) {
		sincro.bloquearPrint();
		System.out.println(mesa);
		sincro.desbloquearPrint();
	}
}
