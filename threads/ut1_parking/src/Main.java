
public class Main {
	final static int SIMULACOCHES = 100;
	final static int MAXPARKING = 100;
	
	public static void main(String[] args) {
		Coche[] coche = new Coche[SIMULACOCHES];
		Parking parking = new Parking(MAXPARKING);
		Sincro sincro = new Sincro(MAXPARKING, SIMULACOCHES);
		System.out.println("Iniciando simulacion...");
		long inicioSimulacion = System.currentTimeMillis();
		for (int i = 0; i < coche.length; i++) {
			coche[i] = new Coche(i, sincro, parking);
		}
		long finSimulacion = System.currentTimeMillis();
		long tiempoSimulacion = finSimulacion - inicioSimulacion;
		long tiempoMedioEstacionamiento = (
				parking.getTiempoEstacionamientos() / parking.getContadorEstacionamientos());
		System.out.println("Tiempo simulacion: " + tiempoSimulacion);
		System.out.println("Han entrado al parking: " + parking.getContadorEstacionamientos());
		System.out.println("Tiempo medio de estacionamiento: " + tiempoMedioEstacionamiento);
		
		
	}
}