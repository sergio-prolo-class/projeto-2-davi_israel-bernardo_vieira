package ifsc.joe.domain.impl;

import ifsc.joe.Interfaces.ComMontaria;
import ifsc.joe.Interfaces.Guerreiro;
import ifsc.joe.enums.Direcao;
import ifsc.joe.ui.Tela;
import ifsc.joe.utils.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class Cavaleiro extends Personagem implements Guerreiro, ComMontaria {

    public static final String IMG_MONTADO = "Cavaleiro";
    public static final String IMG_DESMONTADO = "CavaleiroDesmontado";
    public static final int RAIO_ATAQUE = 50;
    private float alpha = 1.0f;
    private Timer fadeTimer;

    private boolean montado = true;

    public Cavaleiro(int x, int y) {
        super(x, y, false, null, 120, 30, 10, RAIO_ATAQUE);
    }

    // =========================
    // DESENHO
    // =========================
    @Override
    public void desenhar(Graphics g, JPanel painel) {

        // Desenha aura apenas se estiver selecionado
        if (painel instanceof Tela tela && tela.getPersonagemAtivo() == this) {
            desenharAuraAtaque(g);
        }

        String imagemBase = montado ? IMG_MONTADO : IMG_DESMONTADO;
        String imagemFinal = imagemBase + (atacando ? "2" : "");

        this.icone = carregarImagem(imagemFinal);

        // ===== AQUI É O FADE OUT =====
        Graphics2D g2 = (Graphics2D) g;
        Composite original = g2.getComposite();

        g2.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER,
                        alpha));

        g.drawImage(this.icone, posX, posY, painel);
        g2.setComposite(original);
        desenharVida(g);
    }

    // =========================
    // MONTARIA
    // =========================
    @Override
    public void alternarMontado() {
        montado = !montado;
    }

    @Override
    public boolean isMontado() {
        return montado;
    }

    // =========================
    // MOVIMENTO
    // =========================
    @Override
    public void mover(Direcao dir, int w, int h) {
        int velocidade = montado ? 20 : 10;

        switch (dir) {
            case CIMA -> posY -= velocidade;
            case BAIXO -> posY += velocidade;
            case ESQUERDA -> posX -= velocidade;
            case DIREITA -> posX += velocidade;
        }

        posX = Math.max(0, Math.min(w - 50, posX));
        posY = Math.max(0, Math.min(h - 50, posY));
    }

    // =========================
    // ATAQUE (FUNCIONA MONTADO E DESMONTADO)
    // =========================
    @Override
    public void atacar() {
        iniciarAnimacaoAtaque();
    }

    @Override
    public void atacarTodosProximos(List<Personagem> alvos) {
        AudioPlayer.playSound("som_ataque_espada.wav");

        this.atacando = true;

        alvos.stream()
                .filter(alvo -> alvo != this)
                .filter(alvo -> calcularDistancia(alvo) <= RAIO_ATAQUE)
                .forEach(alvo -> alvo.sofrerDano(getAtaque()));

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (Exception ignored) {
            }
            atacando = false;
        }).start();
    }

    // =========================
    // MÉTODOS AUXILIARES
    // =========================
    private void iniciarAnimacaoAtaque() {
        this.atacando = true;

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            this.atacando = false;
        }).start();
    }

    private double calcularDistancia(Personagem outro) {
        return Math.sqrt(
                Math.pow(posX - outro.getPosX(), 2) +
                        Math.pow(posY - outro.getPosY(), 2));
    }

    private Image carregarImagem(String nome) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource(nome + ".png"))).getImage();
    }

    private void iniciarFadeOut() {

        if (fadeTimer != null && fadeTimer.isRunning()) {
            fadeTimer.stop();
        }

        alpha = 1.0f;

        fadeTimer = new Timer(60, e -> { // intervalo MAIOR
            alpha -= 0.04f; // decremento MENOR

            if (alpha <= 0f) {
                alpha = 1.0f;
                fadeTimer.stop();
            }
        });

        fadeTimer.start();
    }
}
