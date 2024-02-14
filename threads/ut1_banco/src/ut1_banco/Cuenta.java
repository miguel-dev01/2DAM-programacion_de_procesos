package ut1_banco;

public class Cuenta {

	private double saldo;

	public Cuenta(int saldo) {
		this.saldo = saldo;
	}

	public double getSaldo() {
		return saldo;
	}

	public void reintegrar(double saldo) {
		this.saldo -= saldo;
		if (this.saldo < 0) {
			System.out.println("Saldo negativo -->" + this.saldo);
		}
	}

	public void ingresar(double saldo) {
		this.saldo += saldo;
	}

}
