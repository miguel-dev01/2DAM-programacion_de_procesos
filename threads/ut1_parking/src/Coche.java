import java.util.Random;

public class Coche extends Thread {

	private Sincro sincro;
	private int id;
	private Parking parking;
	private long tiempoEntrada;
	private long tiempoSalida;
	
	public Coche(int id, Sincro sincro, Parking parking) {
		this.id = id;
		this.sincro = sincro;
		this.parking = parking;
		this.start();
	}
	
	public void run( ) {
		Random aleatorio = new Random();
		// Esperamos aqui primero que todos los hilos esten listos
		sincro.esperarInicioConcurrenteCoches();
		// Generamos una espera aleatoria antes de entrar
		try {
			// TODO hay coches que se quedan fuera. no deben volver a entrar si ya han entrado
			Thread.sleep(aleatorio.nextInt(100));
			parking.entrarParking();
			tiempoEntrada = System.currentTimeMillis();
			System.out.println("El coche " + id + " entra al parking");
			parking.salirParking();
			System.out.println("El coche " + id + " sale del parking");
			tiempoSalida = System.currentTimeMillis();
			parking.addTiempoEstacionamientos(tiempoEntrada + tiempoSalida);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Notificamos al padre que hemos terminado y otro coche empezara a entrar
		sincro.notificarFinCoches();
		System.out.println("Coche " + id + " finalizado");
	}

}
