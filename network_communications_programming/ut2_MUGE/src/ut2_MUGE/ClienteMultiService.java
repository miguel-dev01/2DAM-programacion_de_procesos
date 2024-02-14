package ut2_MUGE;

import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ClienteMultiService extends Thread {

	private Socket socket;
	private PrintWriter flujoS;
	private Scanner flujoE;
	private PrintWriter flujoSMultiCast;
	private Semaphore lock;
	
	public ClienteMultiService(Socket socket, PrintWriter flujoS, Scanner flujoE, 
				PrintWriter flujoSMultiCast, Semaphore lock) {
		this.socket = socket;
		this.flujoS = flujoS;
		this.flujoE = flujoE;
		this.flujoSMultiCast = flujoSMultiCast;
		this.lock = lock;
		
		this.start();
	}
	
	public void run() {
		flujoS.println("Bienvenido al servicio de generacion de claves publicas.");
		flujoS.println("Indica los comandos(genera/publica/privada/halt/quit)");
		flujoS.flush();
		boolean fin = false;
		FirmaDigital firma = new FirmaDigital();
		
		while(!fin) {
		    String comando = flujoE.next();
			System.out.println(comando);
			if(comando.contains("quit")) {
				fin = true;
			} else {
				if(comando.contains("genera")) {
					firma.generarClaves();
					flujoS.println("Claves generadas exitosamente");
					flujoS.flush();
				} else if(comando.contains("publica")) {
					if(firma.existenClaves()) {
						String mensaMulti = socket.getInetAddress().getHostAddress() + 
								"\n" + firma.getClavePub() + "\nHora actual: " + obtenerHoraActual();
						try {
							lock.acquire();
							flujoSMultiCast.println(mensaMulti);
							flujoSMultiCast.flush();
							lock.release();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						flujoS.println("Las claves no han sido generadas");
						flujoS.flush();
					}
				} else if(comando.contains("privada")) {
					if(firma.existenClaves()) {
						flujoS.println(firma.getClavePriv());
						flujoS.flush();
					} else {
						flujoS.println("Las claves no han sido generadas");
						flujoS.flush();
					}
				} else if(comando.contains("halt")) {
					MultiCastGenerator.finalizarServidor();
					fin = true;
				} else {
					flujoS.println("Comando no reconocido en el servidor");
					flujoS.flush();
				}
				
			}
			
		} // fin bucle
		flujoE.close();
		flujoS.close();
		
	}
	
    public static String obtenerHoraActual() {
        LocalDateTime locaDate = LocalDateTime.now();
        int hours = locaDate.getHour();
        int minutes = locaDate.getMinute();
        int seconds = locaDate.getSecond();
        return hours + ":" + minutes + ":" + seconds;
    }
	
}
