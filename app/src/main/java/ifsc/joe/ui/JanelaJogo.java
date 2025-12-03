package ifsc.joe.ui;

import javax.swing.*;

/**
 * Classe responsável pela configuração e exibição da janela principal do jogo.
 */
public class JanelaJogo {

    private static final String TITULO = "Java of Empires";
    private final JFrame frame;
    private final PainelControles painelControles;

    public JanelaJogo() {
        this.frame = new JFrame(TITULO);
        this.painelControles = new PainelControles();

        this.configurarJanela();
    }

    /**
     * Configura o conteúdo da janela
     */
    private void configurarJanela() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(painelControles.getPainelPrincipal());
        frame.pack();
        frame.setLocationRelativeTo(null); // Centralizar na tela
    }

    /**
     * Torna a janela visível.
     */
    public void exibir() {
        frame.setVisible(true);
    }
}
