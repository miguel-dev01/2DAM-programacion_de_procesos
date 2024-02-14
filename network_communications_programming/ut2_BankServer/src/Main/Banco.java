package Main;

public class Banco {
	
	private Cuenta[] cuenta;
	
	public Banco(int numCuenta) {
		cuenta = new Cuenta[numCuenta];
		
		for (int i = 0; i < numCuenta; i++) {
			cuenta[i] = new Cuenta(0);
		}
	}

	public void setIngreso(int id, int importe) {
		this.cuenta[id].setIngreso(importe);
	}

	public int getSaldo(int id) {
		return this.cuenta[id].getSaldo();
	}

	public int getReintegro(int id, int importe) {
		return this.cuenta[id].setReintegro(importe);
	}

}
