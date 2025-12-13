package ifsc.joe.domain.impl;

import ifsc.joe.Interfaces.Coletador;
import ifsc.joe.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Aldeao extends Personagem implements Coletador {
    public static final String NOME_IMAGEM = "aldeao";
    private float alpha = 1.0f;
    private Timer fadeTimer;


    public Aldeao(int x, int y) {
        // vida, ataque, defesa
        super(x, y, false, null, 100, 0, 5, 0);
    }

    @Override
    public void coletar() {
        //Eu coleto se tiver tempo :)
    }

    // Desenha o Aldeão
    @Override
    public void desenhar(Graphics g, JPanel painel) {
        // verificando qual imagem carregar
        this.icone = this.carregarImagem(NOME_IMAGEM + (atacando ? "2" : ""));
        // desenhando de fato a imagem no pai
        g.drawImage(this.icone, this.posX, this.posY, painel);

        // ===== AQUI É O FADE OUT =====
        Graphics2D g2 = (Graphics2D) g;
        Composite original = g2.getComposite();

        g2.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER,
                        alpha
                )
        );

        g2.setComposite(original);
        desenharVida(g);
    }
    /**
     * Metodo auxiliar para carregar uma imagem do disco
     *
     * @param imagem Caminho da imagem
     * @return Retorna um objeto Image
     */
    private Image carregarImagem(String imagem) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("./" + imagem + ".png"))).getImage();
    }

    private void iniciarFadeOut() {

        if (fadeTimer != null && fadeTimer.isRunning()) {
            fadeTimer.stop();
        }

        alpha = 1.0f;

        fadeTimer = new Timer(60, e -> {   // intervalo MAIOR
            alpha -= 0.04f;                // decremento MENOR

            if (alpha <= 0f) {
                alpha = 1.0f;
                fadeTimer.stop();
            }
        });

        fadeTimer.start();
    }
}
