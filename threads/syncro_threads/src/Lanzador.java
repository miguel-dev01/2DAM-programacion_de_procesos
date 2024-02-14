
public class Lanzador {
	public static void main(String[] args) {
		int maxHilos = 100;
		HiloSincro01[] hilos = new HiloSincro01[maxHilos];
		// inicializamos los hilos
		for(int i = 0; i < maxHilos; i++) {
			hilos[i] = new HiloSincro01(i);
		}
	}
}