
public class HiloSincro01 extends Thread {
	boolean fin = false;
	public static int contador = 0;
	private int idHilo;
	public HiloSincro01(int idHilo) {
		this.idHilo = idHilo;
		this.start();
	}
	public void run() {
		while(!fin) {
			cambiarContador();
		}
	}
	private synchronized void cambiarContador() {
		if(this.idHilo == contador) {
			System.out.println("Hola soy " + this.idHilo);
			contador++;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if(contador == 100) {
			contador = 0;
		}
	}
}
