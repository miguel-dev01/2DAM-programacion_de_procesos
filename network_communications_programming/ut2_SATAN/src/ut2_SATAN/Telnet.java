package ut2_SATAN;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Telnet {

	private JFrame frmAccesoAServidor;
	private JTextField textConnection;
	private JTextField textEnviar;
	
	// private String serverIP = "127.0.0.1";
	private int serverPort = 8888;
	private Socket socket;
	private InetAddress host;
	private PrintWriter flujoS;
	private boolean conectado = false;
	private JTextField txtStatus;
	private Receptor receptorMensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Telnet window = new Telnet();
					window.frmAccesoAServidor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Telnet() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAccesoAServidor = new JFrame();
		frmAccesoAServidor.setResizable(false);
		frmAccesoAServidor.setTitle("Acceso a servidor con Telnet");
		frmAccesoAServidor.setBounds(100, 100, 587, 429);
		frmAccesoAServidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAccesoAServidor.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Servidor Telnet");
		lblNewLabel.setBounds(228, 12, 126, 15);
		frmAccesoAServidor.getContentPane().add(lblNewLabel);
		
		JLabel lblUrl = new JLabel("URL");
		lblUrl.setBounds(26, 56, 70, 15);
		frmAccesoAServidor.getContentPane().add(lblUrl);
		
		textConnection = new JTextField();
		textConnection.setText("127.0.0.1");
		textConnection.setBounds(80, 54, 179, 19);
		frmAccesoAServidor.getContentPane().add(textConnection);
		textConnection.setColumns(10);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(271, 51, 135, 25);
		frmAccesoAServidor.getContentPane().add(btnConectar);
		
		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnDesconectar.setBounds(418, 51, 137, 25);
		frmAccesoAServidor.getContentPane().add(btnDesconectar);
		
		JLabel lblRecibido = new JLabel("RECIBIDO");
		lblRecibido.setBounds(26, 98, 70, 15);
		frmAccesoAServidor.getContentPane().add(lblRecibido);
		
		JLabel lblEnviar = new JLabel("ENVIAR");
		lblEnviar.setBounds(26, 313, 70, 15);
		frmAccesoAServidor.getContentPane().add(lblEnviar);
		
		textEnviar = new JTextField();
		textEnviar.setColumns(10);
		textEnviar.setBounds(80, 311, 373, 19);
		frmAccesoAServidor.getContentPane().add(textEnviar);
		
		JButton btnEnviar = new JButton("ENVIAR");
		btnEnviar.setBounds(465, 308, 90, 25);
		frmAccesoAServidor.getContentPane().add(btnEnviar);
		
		txtStatus = new JTextField();
		txtStatus.setEditable(false);
		txtStatus.setBounds(26, 350, 529, 19);
		frmAccesoAServidor.getContentPane().add(txtStatus);
		txtStatus.setColumns(10);
		
		JScrollPane textScrollPane = new JScrollPane();
		textScrollPane.setBounds(26, 125, 529, 166);
		frmAccesoAServidor.getContentPane().add(textScrollPane);
		
		JTextArea textArea = new JTextArea();
		textScrollPane.setViewportView(textArea);
		
		/**
		 * ACCIONES
		 * */
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					host = InetAddress.getByName(textConnection.getText());
					socket = new Socket(host, serverPort);
					conectado = true;
					txtStatus.setText("Conectado a "+ textConnection.getText() + ":" + serverPort);
					textConnection.setEditable(false);
					textEnviar.grabFocus();
					btnEnviar.setEnabled(true);
					textEnviar.setEditable(true);
					btnDesconectar.setEnabled(true);
					btnConectar.setEnabled(false);
					flujoS = new PrintWriter(socket.getOutputStream());
					receptorMensaje = new Receptor(socket, textArea, textScrollPane);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					conectado = false;
					btnEnviar.setEnabled(false);
					textEnviar.setEditable(false);
					textEnviar.setText("");
					txtStatus.setText("Desconectado del servidor");
					btnConectar.setEnabled(true);
					btnDesconectar.setEnabled(false);
					receptorMensaje.finalizar();
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		textEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendCommand();
			}
		});
		
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendCommand();
			}
		});
		
		
	} // cierre initialize()
	
	private void sendCommand() {
		String comando = textEnviar.getText();
		flujoS.println(comando);
		flujoS.flush();
		
		textEnviar.setText("");
		textEnviar.grabFocus(); // El que este seleccionado actualmente
	}
}
