package ut2_MUGE;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Receptor extends Thread {
	
	private Socket socket;
	private JTextArea textArea;
	private JScrollPane textScrollPane;
	private Scanner flujoE;
	private boolean fin = false;

	public Receptor(Socket socket, JTextArea textArea, JScrollPane textScrollPane) {
		this.socket = socket;
		this.textArea = textArea;
		this.textScrollPane = textScrollPane;
		
		this.start();
	}
	
	public void run() {
		String recibido;
		int posicion;
		// Enlazamos con el socket del servidor
		try {
			flujoE = new Scanner(socket.getInputStream());
			while (!fin) {
				recibido = flujoE.nextLine();
				textArea.append(recibido + "\n");
				
				posicion = textScrollPane.getVerticalScrollBar().getMaximum();
				textScrollPane.getVerticalScrollBar().setValue(posicion);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void finalizar() {
		flujoE.close();
		fin = true;
	}

}
