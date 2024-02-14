package ut1_NoCorea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Estacion extends Thread {

    private int id;
    private Sincro sincro;
    private BufferedReader flujoE;
    private PrintWriter flujoS;

    public Estacion(int id, Sincro sincro, PipedReader receptor, PipedWriter emisor) {
        this.id = id;
        this.sincro = sincro;
        this.flujoE = new BufferedReader(receptor);
        this.flujoS = new PrintWriter(emisor);
        this.start();
    }

    public void run() {
        sincro.notificar(); // notificamos al padre que van estando listas
        Random random = new Random();

        if (random.nextInt(11) >= 8) {
            // ha habido movimiento
            sincro.bloquearRadio();
            flujoS.println(id);
            sincro.desbloquearRadio();

            try {
                String orden = flujoE.readLine();
                if (orden.equals("atacar")) {
                    sincro.bloquearRadio();
                    flujoS.println("Estacion Nº: " + id + " atacando...");
                    sincro.desbloquearRadio();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // ejecutar espera aleatoria de 0 - 1 seg
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
