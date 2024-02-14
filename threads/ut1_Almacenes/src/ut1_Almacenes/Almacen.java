package ut1_Almacenes;

public class Almacen {

	private int productos;
	
	public Almacen(int productos) {
		this.productos = productos;
	}
	
	public void decrementarProducto() {
		this.productos--;
	}
	
	public int cantidadProductos() {
		return this.productos;
	}
	
}
