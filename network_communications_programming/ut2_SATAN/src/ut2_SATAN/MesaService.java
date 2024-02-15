package ut2_SATAN;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MesaService extends Thread {

	private Integer id;
	private PrintWriter flujoS;
	private Scanner flujoE;
	private ContadorMesa contadorMesa;
	private int mesaAsignada;
	private MCastSender serverUDP;
	
	public MesaService(int id, Socket socket, ContadorMesa contadorMesa, 
			MCastSender serverUDP) throws IOException {
		this.id = id;
		this.flujoS = new PrintWriter(socket.getOutputStream());
		this.flujoE = new Scanner(socket.getInputStream());
		this.contadorMesa = contadorMesa;
		this.mesaAsignada = 0;
		this.serverUDP = serverUDP;
		
		this.start();
	}
	
	public void run() {
		flujoS.println("Servicio de Atención al Alumno Numérico (SATAN)");
		flujoS.println("Estas conectado a SATAN, indica una mesa para comenzar:");
		flujoS.println("Comandos disponibles: mesa i | next | reset | numero | print | start | exit");
		flujoS.flush();
		boolean fin = false;
		
		while (!fin) {
			String comando = flujoE.nextLine();
			System.out.println(comando);
			
			if (comando.equals("exit")) {
				if (mesaAsignada != 0) {
					contadorMesa.clearAsignacion(mesaAsignada);
					mesaAsignada = 0;
					flujoS.println("Has salido de la mesa");
				}
				fin = true;
				flujoS.flush();
			} else {
				if (comando.contains("mesa")) {
					String[] partes = comando.split(" ");
					mesaAsignada = Integer.parseInt(partes[1]);
					boolean asignarMesa = contadorMesa.asignaMesa(mesaAsignada, id);
					if (asignarMesa) {
						flujoS.println("Ok -> Asignado a la mesa " + mesaAsignada);
					} else {
						flujoS.println("No se ha podido asignar a una mesa");
					}
					flujoS.flush();
				} else if (comando.equals("next")) {
					if (mesaAsignada != 0) {
						int nextPerson = contadorMesa.nextContador();
						if (nextPerson != 0) {
							String mensaje = "Mesa " + mesaAsignada + " pase numero " + nextPerson;
							flujoS.println("Ok -> " + mensaje);
							serverUDP.mCastSend(mensaje);
							contadorMesa.nextContador();
						} else {
							flujoS.println("No hay alumnos que atender");
						}
						flujoS.flush();
					} else {
						flujoS.println("El funcionario no tiene mesa asignada.");
						flujoS.flush();
					}
				} else if (comando.equals("reset")) {
					if (mesaAsignada == 0) {
						flujoS.println("El funcionario no tiene ninguna mesa asociada.");
					} else {
						contadorMesa.clearAsignacion(mesaAsignada);
					}
					flujoS.flush();
				} else if (comando.equals("print")) {
					String mensaje = contadorMesa.getAsignacion(mesaAsignada);
					System.out.println(mensaje);
					flujoS.println(mensaje);
					flujoS.flush();
				} else if (comando.equals("start")) {
					this.contadorMesa.contadorAlumno = 0;
					this.contadorMesa.peticionAlumno = 0;
				} else if (comando.equals("numero")) {
					int siguienteAlumno = contadorMesa.nextAlumno();
					flujoS.println("Numero alumno: " + siguienteAlumno + ", espere a ser atendido");
					flujoS.flush();
				} else {
					flujoS.println("Comando no reconocido");
					flujoS.flush();
				}
			}
			
		} // cierre While
		
	} // cierre run()
	
}
