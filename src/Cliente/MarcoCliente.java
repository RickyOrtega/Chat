package Sockets.Cliente;

import javax.swing.*;

public class MarcoCliente extends JFrame {
    public MarcoCliente() {
        setLAF();

        setBounds(600, 300, 280, 350);
        LaminaMarcoCliente miLamina = new LaminaMarcoCliente("Cliente");
        setResizable(false);
        add(miLamina);

        setVisible(true);
    }

    private void setLAF(){
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