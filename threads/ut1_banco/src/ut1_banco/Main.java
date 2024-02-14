package ut1_banco;

public class Main {
	
	static final int MAX_CLIENTES = 240;
	
	public static void main(String[] args) {
		// Creamos los hilos de los clientes
		Cliente[] clientes = new Cliente[MAX_CLIENTES];
		Sincro sincro = new Sincro(MAX_CLIENTES);
		Cuenta cuenta = new Cuenta(100);
		
		// Variables para saber el tiempo total de ejecucion
		long inicioSimulacion = System.currentTimeMillis();
		System.out.println("Inicio de la simulacion");
		long finSimulacion = 0;
		long tiempoSimulacion = inicioSimulacion - finSimulacion;
		
		// Inicializamos los hilos, estos hilos hacen la accion de ingresar / reintegrar 100€
		for (int id = 0; id < 80; id++) {
			if (id < 40) {
				clientes[id] = new Cliente(id, cuenta, sincro, Operacion.INGRESO, 100);
			} else {
				clientes[id] = new Cliente(id, cuenta, sincro, Operacion.REINTEGRO, 100);
			}
		}
		
		// Inicializamos los hilos, estos hilos hacen la accion de ingresar / reintegrar 50€
		for (int id = 80; id < 120; id++) {
			if (id < 100) {
				clientes[id] = new Cliente(id, cuenta, sincro, Operacion.INGRESO, 50);
			} else {
				clientes[id] = new Cliente(id, cuenta, sincro, Operacion.REINTEGRO, 50);
			}
		}
		
		// Inicializamos los hilos, estos hilos hacen la accion de ingresar / reintegrar 20
		for (int id = 120; id < 240; id++) {
			if (id < 180) {
				clientes[id] = new Cliente(id, cuenta, sincro, Operacion.INGRESO, 20);
			} else {
				clientes[id] = new Cliente(id, cuenta, sincro, Operacion.REINTEGRO, 20);
			}
		}
		// debemos esperar a que todos se inicien.
		// Al ser un CountDownLatch, el padre no continua, se espera
		sincro.esperarInicioConcurrenteCliente();
		
		finSimulacion = System.currentTimeMillis();
		tiempoSimulacion = finSimulacion - inicioSimulacion;
		System.out.println("Tiempo de simulacion " + tiempoSimulacion);
		
		System.out.println("Saldo final de la cuenta " + cuenta.getSaldo());
	}
}
