package prueba2_nadc;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Norad {
	static final int MAX_MISILES = 10;
	
	public static void main(String[] args) throws IOException {
		
		Sincro sincro = new Sincro(MAX_MISILES);
		Misil[] misil = new Misil[MAX_MISILES];
		Scanner teclado = new Scanner(System.in);
		PipedWriter[] emisor = new PipedWriter[MAX_MISILES];
		PrintWriter[] flujoSAtacar = new PrintWriter[MAX_MISILES];
		
		for (int i = 0; i < MAX_MISILES; i++) {
			emisor[i] = new PipedWriter();
			flujoSAtacar[i] = new PrintWriter(emisor[i]);
			misil[i] = new Misil(i, sincro, emisor[i]);
		}
		
		sincro.esperarMisilListo();
		System.out.println("Todos los misiles armados y listos.");
		
		boolean fin = false;
		while(!fin) {
			System.out.println("Introduce la orden 'atacar' para preparar a los misiles: ");
			String ordenAtacar = teclado.nextLine();
			if (ordenAtacar.contains("atacar")) {
				fin = true;
				sincro.countDownMisilListo();
				for (int i = 0; i < MAX_MISILES; i++) {
					flujoSAtacar[i].println("ataca");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}
		
	}
	
}
