package ut2_SATAN;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class MulticastListener {

    private JFrame frmReceptorMulticast;
    private JTextField txtDireccionMulticast;
    private JTextField txtPuerto;
    private JTextArea textArea;
    private MulticastSocket multicastSocket;
    private InetAddress multicastGroup;
    private int port;
    private boolean listening = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MulticastListener window = new MulticastListener();
                    window.frmReceptorMulticast.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MulticastListener() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmReceptorMulticast = new JFrame();
        frmReceptorMulticast.setResizable(false);
        frmReceptorMulticast.setTitle("Receptor Multicast por Miguel Sánchez");
        frmReceptorMulticast.setBounds(100, 100, 587, 429);
        frmReceptorMulticast.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmReceptorMulticast.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Receptor Multicast");
        lblNewLabel.setBounds(228, 12, 126, 15);
        frmReceptorMulticast.getContentPane().add(lblNewLabel);

        JLabel lblDireccionMulticast = new JLabel("Dirección Multicast");
        lblDireccionMulticast.setBounds(26, 56, 150, 15);
        frmReceptorMulticast.getContentPane().add(lblDireccionMulticast);

        txtDireccionMulticast = new JTextField();
        txtDireccionMulticast.setText("239.0.0.1");
        txtDireccionMulticast.setBounds(180, 54, 179, 19);
        frmReceptorMulticast.getContentPane().add(txtDireccionMulticast);
        txtDireccionMulticast.setColumns(10);

        JLabel lblPuerto = new JLabel("Puerto");
        lblPuerto.setBounds(26, 90, 70, 15);
        frmReceptorMulticast.getContentPane().add(lblPuerto);

        txtPuerto = new JTextField();
        txtPuerto.setText("8888");
        txtPuerto.setColumns(10);
        txtPuerto.setBounds(180, 88, 179, 19);
        frmReceptorMulticast.getContentPane().add(txtPuerto);

        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.setBounds(389, 51, 135, 25);
        frmReceptorMulticast.getContentPane().add(btnIniciar);

        JButton btnDetener = new JButton("Detener");
        btnDetener.setFont(new Font("Dialog", Font.BOLD, 12));
        btnDetener.setBounds(389, 86, 135, 25);
        frmReceptorMulticast.getContentPane().add(btnDetener);

        JScrollPane textScrollPane = new JScrollPane();
        textScrollPane.setBounds(26, 125, 529, 233);
        frmReceptorMulticast.getContentPane().add(textScrollPane);

        textArea = new JTextArea();
        textScrollPane.setViewportView(textArea);

        btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (!listening) {
                    try {
                        port = Integer.parseInt(txtPuerto.getText());
                        multicastGroup = InetAddress.getByName(txtDireccionMulticast.getText());
                        multicastSocket = new MulticastSocket(port);
                        multicastSocket.joinGroup(multicastGroup);
                        listening = true;
                        txtDireccionMulticast.setEditable(false);
                        txtPuerto.setEditable(false);
                        btnDetener.setEnabled(true);
                        btnIniciar.setEnabled(false);
                        receiveMessages();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnDetener.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listening) {
                    try {
                        multicastSocket.leaveGroup(multicastGroup);
                        multicastSocket.close();
                        listening = false;
                        txtDireccionMulticast.setEditable(true);
                        txtPuerto.setEditable(true);
                        btnDetener.setEnabled(false);
                        btnIniciar.setEnabled(true);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void receiveMessages() {
        Thread receiverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                while (listening) {
                    try {
                        multicastSocket.receive(packet);
                        String message = new String(packet.getData(), 0, packet.getLength());
                        textArea.append("Recibido: " + message + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        receiverThread.start();
    }
}
