package ut1_filosofos;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		int nFilosofos = 5;
		CyclicBarrier inicio = new CyclicBarrier(nFilosofos); // Inicio de los filosofos, para que empiecen a la vez
		Filosofo[] filosofos = new Filosofo[nFilosofos];
		Semaphore[] palilloEspera = new Semaphore[nFilosofos];
		int[][] palillos = {{4,0},{0,1},{1,2},{2,3},{3,4}};
		
		// Inicializamos los palillos, con un semaforo que se asocia a cada uno de estos 
		for(int i = 0; i < palilloEspera.length; i++) {
			palilloEspera[i] = new Semaphore(1);
		}
		
		int id = 0;
		for (Filosofo filosofo : filosofos) {
			filosofo = new Filosofo(id, palilloEspera, palillos, inicio);
			id++;
		}
	}

}
