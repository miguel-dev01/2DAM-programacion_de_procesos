package Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BankService extends Thread {

	private int id;
	private Socket socket; // cada conexion de telnet
	private PrintWriter flujoS;
	private Scanner flujoE;
	private Banco banco;
	private int cuenta;
	
	public BankService(int id, Socket socket, Banco banco) {
		this.id = id;
		this.socket = socket;
		this.banco = banco;
		this.cuenta = -1;
		
		this.start();
	}
	
	public void run() {
		boolean fin = false;
		System.out.println("Conexion desde " + socket.getInetAddress().getHostAddress());
		// Enlazamos los flujos
		try {
			// Flujo de salida que va al socket(servidor)
			flujoS = new PrintWriter(socket.getOutputStream());
			flujoE = new Scanner(socket.getInputStream());
			flujoS.println("Bienvenido a la banca online. Le atiende el hilo " + id);
			flujoS.flush();
			while (!fin) {
				String comando = flujoE.next();
				flujoS.println("Recibido > " + comando);
				if (comando.contains("quit")) {
					fin = true;
				} else {
					if (cuenta == -1) {
						flujoS.println("Debe asignar una cuenta para operar: ");
					}
					processCommand(comando);
				}
				flujoS.flush();
			}
			flujoS.close();
			flujoE.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void processCommand(String comando) {
		// Hacer saldo, ingreso y reintegro
		int importe = 0;
		if (comando.contains("cuenta")) {
			cuenta = flujoE.nextInt();
			if (cuenta >= 0) {
				flujoS.println("Seleccionada la cuenta " + cuenta);
			} else {
				cuenta = -1;
			}
			
		} else if (comando.contains("ingreso")) {
			importe = flujoE.nextInt();
			banco.setIngreso(cuenta, importe);
		} else if (comando.contains("saldo")) {
			flujoS.println("El saldo de la cuenta " + cuenta + " es " + banco.getSaldo(cuenta));
		} else if (comando.contains("reintegro")) {
			importe = flujoE.nextInt();
			int cantidad = banco.getReintegro(cuenta, importe);
			if (cantidad != -1) {
				flujoS.println("Retirado de cuenta " + cuenta + ", una cantidad de: " + cantidad);
			} else {
				flujoS.println("En la cuenta " + cuenta + " no hay saldo suficiente");
			}
		}
		flujoS.flush();
	}

}
