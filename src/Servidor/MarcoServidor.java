package Servidor;

import Cliente.PaqueteEnvio;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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
            ServerSocket servidor = new ServerSocket(9090);

            String nick;
            String ip;
            String mensaje;

            PaqueteEnvio paqueteRecibido;

            while (true){
                Socket socket = servidor.accept();

/*                DataInputStream flujoEntrada = new DataInputStream(socket.getInputStream());

                String mensajeTexto = flujoEntrada.readUTF();

                areaTexto.append(mensajeTexto + "\n");*/

                ObjectInputStream paqueteDatos = new ObjectInputStream(socket.getInputStream());

                paqueteRecibido = (PaqueteEnvio) paqueteDatos.readObject();

                nick = paqueteRecibido.getNick();

                ip = paqueteRecibido.getIp();

                mensaje = paqueteRecibido.getMensaje();

                areaTexto.append("\n" + nick + ": ");
                areaTexto.setFont(areaTexto.getFont().deriveFont(2,12f));
                areaTexto.setForeground(Color.GREEN);
                areaTexto.append(mensaje);
                areaTexto.setForeground(Color.BLACK);
                areaTexto.append(" para " + ip);

                Socket enviaDestinatario = new Socket(ip,1998);

                ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());

                paqueteReenvio.writeObject(paqueteRecibido);

                paqueteReenvio.close();

                enviaDestinatario.close(); // Cierra el reenvío

                socket.close(); //Cierra la entrada de datos
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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