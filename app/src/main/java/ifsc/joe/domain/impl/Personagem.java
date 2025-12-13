package ifsc.joe.domain.impl;

import java.awt.*;
import ifsc.joe.enums.Direcao;
import javax.swing.*;
import ifsc.joe.utils.AudioPlayer;

public abstract class Personagem {

    protected int posX, posY;
    protected boolean atacando;
    protected Image icone;

    protected int vida;
    protected int vidaMax;
    protected int ataque;
    protected int defesa;
    protected int alcance;

    // 🔥 FADE OUT
    protected float alpha = 1.0f; // 1 = totalmente visível
    protected boolean morrendo = false;

    public Personagem(int x, int y, boolean atacando, Image icone,
            int vida, int ataque, int defesa, int alcance) {

        this.posX = x;
        this.posY = y;
        this.atacando = atacando;
        this.icone = icone;

        this.vida = vida;
        this.vidaMax = vida; // vida máxima definida no início
        this.ataque = ataque;
        this.defesa = defesa;
        this.alcance = alcance;
    }

    // ==================================================
    // DESENHO
    // ==================================================
    public abstract void desenhar(Graphics g, JPanel painel);

    protected void aplicarFade(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, alpha));
    }

    // ==================================================
    // AURA DE ATAQUE
    // ==================================================
    protected void desenharAuraAtaque(Graphics g) {

        if (alcance <= 0 || icone == null)
            return;

        Graphics2D g2d = (Graphics2D) g;
        Color corOriginal = g2d.getColor();
        Stroke tracoOriginal = g2d.getStroke();

        g2d.setColor(new Color(0, 150, 255, 50));

        int diametro = alcance * 2;
        int larguraIcone = icone.getWidth(null);
        int alturaIcone = icone.getHeight(null);

        int auraX = posX - alcance + larguraIcone / 2;
        int auraY = posY - alcance + alturaIcone / 2;

        g2d.fillOval(auraX, auraY, diametro, diametro);

        g2d.setColor(new Color(0, 150, 255, 200));
        float[] tracoPontilhado = { 5.0f, 5.0f };
        g2d.setStroke(new BasicStroke(
                1,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f,
                tracoPontilhado,
                0.0f));

        g2d.drawOval(auraX, auraY, diametro, diametro);

        g2d.setColor(corOriginal);
        g2d.setStroke(tracoOriginal);
    }

    // ==================================================
    // VIDA (PNG)
    // ==================================================
    protected void desenharVida(Graphics g) {

        if (vida <= 0)
            return;

        int porcentagem = (vida * 100) / vidaMax;
        String arquivoVida;

        if (porcentagem >= 95)
            arquivoVida = "Vida_Cheia.png";
        else if (porcentagem >= 75)
            arquivoVida = "Vida_75.png";
        else if (porcentagem >= 50)
            arquivoVida = "Vida_50.png";
        else if (porcentagem >= 25)
            arquivoVida = "Vida_25.png";
        else if (porcentagem >= 5)
            arquivoVida = "Vida_5.png";
        else
            arquivoVida = "Vida_0.png";

        Image barra = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/" + arquivoVida));

        g.drawImage(barra, posX, posY - 12, 40, 10, null);
    }

    // ==================================================
    // DANO / MORTE
    // ==================================================
    public void sofrerDano(int dano) {

        int danoReal = Math.max(0, dano - defesa);

        if (danoReal > 0) {
            vida -= danoReal;
            if (vida < 0)
                vida = 0;

            AudioPlayer.playSound("som_dano_morrer.wav");

            // 🔥 INICIA FADE OUT
            if (vida == 0 && !morrendo) {
                morrendo = true;
            }
        }
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    // ==================================================
    // FADE OUT
    // ==================================================
    public void atualizarFade() {
        if (morrendo) {
            alpha -= 0.04f; // velocidade do fade

            if (alpha < 0f) {
                alpha = 0f;
            }
        }
    }

    public boolean podeRemover() {
        return morrendo && alpha <= 0f;
    }

    // ==================================================
    // MOVIMENTO / UTIL
    // ==================================================
    public int getAtaque() {
        return ataque;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean foiClicado(int mouseX, int mouseY) {

        int largura = (icone != null) ? icone.getWidth(null) : 50;
        int altura = (icone != null) ? icone.getHeight(null) : 50;

        return mouseX >= posX && mouseX <= posX + largura &&
                mouseY >= posY && mouseY <= posY + altura;
    }

    public void mover(Direcao direcao, int maxLargura, int maxAltura) {

        switch (direcao) {
            case CIMA -> posY -= 10;
            case BAIXO -> posY += 10;
            case ESQUERDA -> posX -= 10;
            case DIREITA -> posX += 10;
        }

        int larguraIcone = (icone != null) ? icone.getWidth(null) : 50;
        int alturaIcone = (icone != null) ? icone.getHeight(null) : 50;

        posX = Math.min(Math.max(0, posX), maxLargura - larguraIcone);
        posY = Math.min(Math.max(0, posY), maxAltura - alturaIcone);
    }
}
