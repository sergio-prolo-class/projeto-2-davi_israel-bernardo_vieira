package ifsc.joe;

import ifsc.joe.ui.JanelaJogo;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaJogo janela = new JanelaJogo();
            janela.exibir();
        });
    }
}