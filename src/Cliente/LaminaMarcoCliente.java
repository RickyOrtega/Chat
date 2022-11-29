package Sockets.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class LaminaMarcoCliente extends JPanel {

    private JTextField nick;
    private JTextField ip;
    private JTextField campoMensaje;

    private JTextArea campoChat;

    private JButton miBoton;

    public LaminaMarcoCliente(String nombreCliente) {

        nick = new JTextField(5);

        add(nick);

        JLabel texto = new JLabel("-------CHAT-------");

        add(texto);

        ip = new JTextField(8);

        add(ip);

        campoChat = new JTextArea(12,24);

        add(campoChat);

        campoMensaje = new JTextField(20);

        add(campoMensaje);

        miBoton = new JButton("Enviar");

        EnviaTexto et = new EnviaTexto();

        miBoton.addActionListener(et);

        add(miBoton);
    }

    private class EnviaTexto implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Socket socket = new Socket("192.168.56.1", 1998);

/*                DataOutputStream flujoSalida = new DataOutputStream(socket.getOutputStream());

                flujoSalida.writeUTF(campoMensaje.getText());

                flujoSalida.close();*/

                PaqueteEnvio datos = new PaqueteEnvio();

                datos.setNick(nick.getText());
                datos.setIp(ip.getText());
                datos.setMensaje(campoMensaje.getText());

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}