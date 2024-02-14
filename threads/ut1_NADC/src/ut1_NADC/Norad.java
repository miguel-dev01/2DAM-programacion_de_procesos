package ut1_NADC;

import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Norad {
	private final static int MAXMISILES = 100;

	public static void main(String[] args) {
		Sincroniza sincro = new Sincroniza(MAXMISILES); //objeto para la sincronizacion
		Misil[] misiles = new Misil[MAXMISILES];
		PipedWriter[] emisor = new PipedWriter[MAXMISILES]; // Flujo salida -> print()
		PrintWriter[] receptor = new PrintWriter[MAXMISILES];

		for(int i = 0; i < misiles.length; i++) {
			emisor[i] = new PipedWriter();
			receptor[i] = new PrintWriter(emisor[i]); // Esta es la tuberia (pipe)
			misiles[i] = new Misil(i, sincro, emisor[i]);
		}

		sincro.esperarMisilesListos();
		System.out.println("Todos los misiles preparados");
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		while(!fin) {
			System.out.println("Orden > ");
			String comando = sc.nextLine();
			if (comando.contains("atacar")) {
				fin = true;
				sincro.resetearContadores();
				//lanzamos todos los misiles
				sincro.realizarActivacion(); // CountDown
				dobleVerificacion(receptor);
			}
		}
		sincro.esperarMisilesFinalizados(); // esperamos a que todos los misiles terminen
		System.out.println("Numero de aciertos " + sincro.getAciertos());
		System.out.println("Numero de fallos " + sincro.getFallos());
		System.out.println("Total de misiles lanzados " + MAXMISILES);
		System.out.println("Porcentaje de exito " + sincro.getAciertos()*100/MAXMISILES);
		sincro.notificarMisilFinalizado();
	} // cierre Main
	
	private static void dobleVerificacion(PrintWriter[] receptor) {
		for (int i = 0; i < MAXMISILES; i++) {
			try {
				Thread.sleep(1000);
				receptor[i].println("atacar");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

} // cierre clase
