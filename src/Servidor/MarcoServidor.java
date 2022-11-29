package Sockets.Servidor;

import javax.swing.*;
import java.awt.*;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MarcoServidor extends JFrame implements Runnable {

    private JTextArea areaTexto;

    public MarcoServidor() {

        setLAF();

        setResizable(false);
        setBounds(1200, 300, 280, 350);
        JPanel miLamina = new JPanel();
        miLamina.setLayout(new BorderLayout());
        areaTexto = new JTextArea();
        miLamina.add(areaTexto, BorderLayout.CENTER);
        add(miLamina);
        setVisible(true);

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        System.out.println("Iniciando servidor...");

        try {
            ServerSocket servidor = new ServerSocket(1998);

            Socket socket = servidor.accept();

            DataInputStream flujoEntrada = new DataInputStream(socket.getInputStream());

            String mensajeTexto = flujoEntrada.readUTF();

            areaTexto.append(mensajeTexto + "\n");

            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLAF() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {

        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}