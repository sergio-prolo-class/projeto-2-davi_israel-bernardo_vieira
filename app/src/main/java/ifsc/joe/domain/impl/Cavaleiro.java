package ifsc.joe.domain.impl;

import ifsc.joe.Interfaces.ComMontaria;
import ifsc.joe.Interfaces.Guerreiro;
import ifsc.joe.enums.Direcao;
import ifsc.joe.ui.Tela;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Cavaleiro extends Personagem implements Guerreiro, ComMontaria {

    public static final String IMG_MONTADO = "Cavaleiro";
    public static final String IMG_DESMONTADO = "CavaleiroDesmontado";
    public static final int RAIO_ATAQUE = 50;

    private boolean montado = true;

    public Cavaleiro(int x, int y) {
        super(x, y, false, null, 120, 30, 10,RAIO_ATAQUE );
    }

    @Override
    public void desenhar(Graphics g, JPanel painel) {

        // CORREÇÃO: Usa 'instanceof' para checar e converter com segurança,
        // resolvendo os erros 'Cannot resolve symbol Tela' e 'getPersonagemAtivo()'
        if (painel instanceof Tela tela && tela.getPersonagemAtivo() == this) {
            desenharAuraAtaque(g);
        }

        String nomeImagemBase = montado ? IMG_MONTADO : IMG_DESMONTADO;

        // Se estiver atacando, carrega a versão 2
        String nomeImagemFinal = nomeImagemBase + (atacando ? "2" : "");

        this.icone = carregarImagem(nomeImagemFinal);

        g.drawImage(this.icone, this.posX, this.posY, painel);
        desenharVida(g);
    }
    @Override
    public void alternarMontado() {
        montado = !montado;
    }

    @Override
    public boolean isMontado() {
        return montado;
    }

    @Override
    public void mover(Direcao dir, int w, int h) {
        int velocidade = montado ? 20 : 10;

        switch (dir) {
            case CIMA    -> posY -= velocidade;
            case BAIXO   -> posY += velocidade;
            case ESQUERDA-> posX -= velocidade;
            case DIREITA -> posX += velocidade;
        }

        posX = Math.max(0, Math.min(w - 50, posX));
        posY = Math.max(0, Math.min(h - 50, posY));
    }

    @Override
    public void atacar() {
        this.atacando = !this.atacando;
    }

    @Override
    public void atacarTodosProximos(List<Personagem> alvos) {

        this.atacando = true;

        alvos.stream()
                .filter(alvo -> alvo != this)
                .filter(alvo -> calcularDistancia(alvo) <= RAIO_ATAQUE)
                .forEach(alvo -> alvo.sofrerDano(this.getAtaque()));

        new Thread(() -> {
            try {
                Thread.sleep(500);
                this.atacando = false;
            } catch (Exception ignored) {}
        }).start();
    }

    private double calcularDistancia(Personagem outro) {
        return Math.sqrt(Math.pow(this.posX - outro.getPosX(), 2) +
                Math.pow(this.posY - outro.getPosY(), 2));
    }

    private Image carregarImagem(String nome) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("./" + nome + ".png")
        )).getImage();
    }
}
