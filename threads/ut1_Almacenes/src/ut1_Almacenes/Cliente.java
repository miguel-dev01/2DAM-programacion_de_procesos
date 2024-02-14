package ut1_Almacenes;

public class Cliente extends Thread {

	private int id;
	private Almacen almacen;
	private Puerta puerta;
	private Sincro sincro;
	private int paciencia;
	
	public Cliente(int id, Almacen almacen, Puerta puerta, Sincro sincro) {
		this.id = id;
		this.almacen = almacen;
		this.puerta = puerta;
		this.sincro = sincro;
		this.paciencia = 10;
		this.start();
	}

	@Override
	public void run() {
		// notificamos que los clientes van estando listos,
		// podriamos decir que estan esperando en la puerta del almacen
		sincro.notificarClientesListos();
		
		boolean fin = false;
		while (!fin) {
			if (puerta.intentarEntrarAlAlmacen()) {
				System.out.println("El cliente " + id + " ha entrado en el almacen.");
				if (almacen.cantidadProductos() != 0) {
					System.out.println("El cliente " + id + " ha cogido un producto.");
					almacen.decrementarProducto();
				} else {
					System.out.println("El cliente " + id + " no ha podido coger producto y se ha marchado.");
				}
				
				puerta.liberarPuerta();
				fin = true;
			} else {
				System.out.println("El cliente " + id + " no ha podido entrar en el "
						+ "almacen, su paciencia es de " + --paciencia);
				if (paciencia == 0) {
					System.out.println("El cliente " + id + " se ha marchado sin conseguir producto.");
					fin = true;
				}
			}
		}
		
		sincro.finalizarClientesListos();
	}	
	
}
