package Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class LaminaMarcoCliente extends JPanel implements Runnable {

    private JLabel nick;
    private JComboBox ip;
    private JTextField campoMensaje;

    private JTextPane campoChat;

    private JButton miBoton;

    public LaminaMarcoCliente() {

        String nombreCliente = JOptionPane.showInputDialog("Introduce tu nombre de usuario: ");

        nick = new JLabel("Nick: " + nombreCliente, SwingConstants.CENTER);

        add(nick);

        JLabel texto = new JLabel("Online: ");
        texto.setForeground(Color.GREEN);

        add(texto);

        ip = new JComboBox();

        ip.addItem("Usuario 1");
        ip.addItem("Usuario 2");
        ip.addItem("Usuario 3");

        add(ip);

        campoChat = new JTextPane();
        campoChat.setSize(this.getSize().width,this.getSize().height-60);

        add(campoChat);

        campoMensaje = new JTextField(20);

        add(campoMensaje);

        miBoton = new JButton("Enviar");

        EnviaTexto et = new EnviaTexto();

        miBoton.addActionListener(et);

        add(miBoton);

        Thread r = new Thread(this);

        r.start();
    }

    @Override
    public void run() {
        try{
            ServerSocket servidor_cliente = new ServerSocket(1998);

            Socket cliente;

            PaqueteEnvio paqueteRecibido;

            while(true){
                cliente = servidor_cliente.accept();

                ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());

                paqueteRecibido = (PaqueteEnvio) flujoEntrada.readObject();

/*                paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje() + "\n"

                campoChat.getStyledDocument().insertString(paqueteRecibido.getNick();*/

                cliente.close();
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private class EnviaTexto implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
/*

            campoChat.append("Tú:" + campoMensaje.getText());
*/

            try {
                Socket socket = new Socket("192.168.1.2", 9090);

/*                DataOutputStream flujoSalida = new DataOutputStream(socket.getOutputStream());

                flujoSalida.writeUTF(campoMensaje.getText());

                flujoSalida.close();*/

                PaqueteEnvio datos = new PaqueteEnvio();

                datos.setNick(nick.getText());
                datos.setIp(ip.getSelectedItem().toString());
                datos.setMensaje(campoMensaje.getText());

                ObjectOutputStream paqueteDatos = new ObjectOutputStream(socket.getOutputStream());

                paqueteDatos.writeObject(datos);

                socket.close();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}