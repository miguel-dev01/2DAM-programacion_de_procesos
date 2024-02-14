package Main;

public class Cuenta {
	
	private int saldo;
	
	public Cuenta(int saldo) {
		this.saldo = saldo;
	}
	
	public void setIngreso(int importe) {
		this.saldo += importe;
	}

	public int getSaldo() {
		return this.saldo;
	}

	public int setReintegro(int importe) {
		int reintegro = -1;
		if (saldo >= importe) {
			saldo -= importe;
			reintegro = importe;
		}
		return reintegro;
	}
	
}
