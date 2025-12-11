package ifsc.joe.domain.impl;

import java.awt.*;
import ifsc.joe.enums.Direcao;
import javax.swing.*;

public abstract class Personagem {

    protected int posX, posY;
    protected boolean atacando;
    protected Image icone;
    protected int vida;
    protected int vidaMax;    // ADICIONADO
    protected int ataque;
    protected int defesa;

    public Personagem(int x, int y, boolean atacando, Image icone, int vida, int ataque, int defesa) {
        this.posX = x;
        this.posY = y;
        this.atacando = atacando;
        this.icone = icone;
        this.vida = vida;
        this.vidaMax = vida; // vida máxima = valor inicial
        this.ataque = ataque;
        this.defesa = defesa;
    }

    // Cada personagem filho deve chamar desenharVida()
    public abstract void desenhar(Graphics g, JPanel painel);

    protected void desenharVida(Graphics g) {

        int porcentagem = (vida * 100) / vidaMax;

        String arquivoVida;
        if (porcentagem >= 95) arquivoVida = "Vida_Cheia.png";
        else if (porcentagem >= 75) arquivoVida = "Vida_75.png";
        else if (porcentagem >= 50) arquivoVida = "Vida_50.png";
        else if (porcentagem >= 25) arquivoVida = "Vida_25.png";
        else if (porcentagem >= 5) arquivoVida = "Vida_5.png";
        else arquivoVida = "Vida_0.png";

        try {
            Image barra = Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("/" + arquivoVida)
            );

            g.drawImage(barra, posX, posY - 12, 40, 20, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sofrerDano(int dano) {
        int danoReal = Math.max(0, dano - this.defesa);
        this.vida -= danoReal;
        if (this.vida < 0) this.vida = 0;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public int getAtaque() { return ataque; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }

    public boolean foiClicado(int mouseX, int mouseY) {
        int largura = (icone != null) ? icone.getWidth(null) : 50;
        int altura  = (icone != null) ? icone.getHeight(null) : 50;

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
        int alturaIcone  = (icone != null) ? icone.getHeight(null) : 50;

        posX = Math.min(Math.max(0, posX), maxLargura - larguraIcone);
        posY = Math.min(Math.max(0, posY), maxAltura - alturaIcone);
    }
}
