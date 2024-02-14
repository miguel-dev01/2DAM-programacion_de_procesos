package ut1_Almacenes;

public class Main {
	static final int CLIENTES = 200;
	static final int PRODUCTOS = 50;
	
	public static void main(String[] args) {
		
		Cliente[] clientes = new Cliente[CLIENTES];
		Almacen almacen = new Almacen(PRODUCTOS);
		Puerta puerta = new Puerta(); // Semaforo
		Sincro sincro = new Sincro(CLIENTES);
		
		System.out.println("Inicio de la simulacion.\nClientes llegando a los almacenes...");
		
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new Cliente(i, almacen, puerta, sincro);
		}
		
		// El padre esperara a que los clientes finalicen
		sincro.esperarClientesListos();
		
		System.out.println("Simulacion finalizada.");
		System.out.println("Cantidad actual de productos: " + almacen.cantidadProductos() + " unidades");
	}
}
