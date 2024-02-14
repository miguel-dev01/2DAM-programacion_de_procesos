package ut1_05_pipes;

import java.io.PipedWriter;
import java.io.PrintWriter;

public class Productor extends Thread {
	private PrintWriter flujoS;
	public Productor(PipedWriter emisor) {
		flujoS = new PrintWriter(emisor);
	}
	public void run() {
		for(int i = 0; i < 10; i++) {
			String message = "Hola, " + i;
			flujoS.println(message);
		}
	}
}
