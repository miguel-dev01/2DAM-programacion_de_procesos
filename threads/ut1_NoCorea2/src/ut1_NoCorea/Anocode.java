package ut1_NoCorea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Anocode {
    final static int MAX_ESTACIONES = 20;

    public static void main(String[] args) throws IOException {
        Estacion[] estacion = new Estacion[MAX_ESTACIONES];
        Sincro sincro = new Sincro(MAX_ESTACIONES);

        // Primero conectaremos la tuberia
        PipedWriter pipeSalida = new PipedWriter();
        PipedReader pipeEntrada = new PipedReader(pipeSalida);
        // Se necesita para poder leer lo que tiene la tuberia de pipeEntrada
        BufferedReader flujoE = new BufferedReader(pipeEntrada);
        
        // sera una conexion de radio, para cada hilo, y que vendra a parar
        // a este main, que es la central
        PipedWriter[] radioE = new PipedWriter[MAX_ESTACIONES];
        PrintWriter[] flujoAtacar = new PrintWriter[MAX_ESTACIONES];

        Scanner teclado = new Scanner(System.in);
        System.out.println("Iniciando el servicio antiescucha de las estaciones...");
        for (int i = 0; i < estacion.length; i++) {
        	radioE[i] = new PipedWriter();
        	// flujo de Atacar para enviar orden a la estacion
        	flujoAtacar[i] = new PrintWriter(radioE[i]);
            estacion[i] = new Estacion(i, sincro, new PipedReader(radioE[i]), pipeSalida);
        }

        sincro.esperarInicioConcurrenteEstaciones();
        System.out.println("Todas las estaciones están operativas...");

        boolean fin = false;
        while (true) {
            String comando = flujoE.readLine();
            int estacionId = Integer.parseInt(comando);
            System.out.println("Estación que ha detectado movimiento: " + estacionId);
            
            System.out.println("Introduce una orden: ");
            String orden = teclado.nextLine();
            
            if (comando.equals("atacar")) {
            	flujoAtacar[estacionId].println("Estacion " + estacionId + " atacando...");
            	flujoAtacar[estacionId].flush();
            }
            // fin = true;
        }
    }
}
