package ifsc.joe.domain.impl;

import ifsc.joe.Interfaces.Guerreiro;
import ifsc.joe.enums.Direcao;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Arqueiro extends Personagem implements Guerreiro {
    public static final String NOME_IMAGEM = "Arqueiro";

    public Arqueiro(int x, int y) {
        // vida, ataque, defesa
        super(x, y, false, null, 80, 20, 5);
    }

    // Desenha o Arqueiro
    @Override
    public void desenhar(Graphics g, JPanel painel) {
        // verificando qual imagem carregar
        this.icone = this.carregarImagem(NOME_IMAGEM + (atacando ? "2" : ""));
        // desenhando de fato a imagem no pai
        g.drawImage(this.icone, this.posX, this.posY, painel);
        desenharVida(g);
    }

    // Método para atacar
    @Override
    public void atacar() {
        this.atacando = !this.atacando;
    }

    @Override
    public void atacarTodosProximos(List<Personagem> alvos) {
        final int RAIO_ATAQUE = 100; // 100 pixels de distância
        this.atacando = true; // Inicia o efeito visual de ataque

        alvos.stream()
                .filter(alvo -> alvo != this) // Não ataca a si mesmo
                .filter(alvo -> calcularDistancia(alvo) <= RAIO_ATAQUE)
                .forEach(alvo -> alvo.sofrerDano(this.getAtaque()));

        // Desliga o efeito visual após um pequeno atraso (simulado)
        // Em um jogo real, isso seria feito com um Timer ou Thread
        new Thread(() -> {
            try {
                Thread.sleep(500); // 0.5 segundo de ataque
                this.atacando = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private double calcularDistancia(Personagem outro) {
        return Math.sqrt(Math.pow(this.posX - outro.getPosX(), 2) + Math.pow(this.posY - outro.getPosY(), 2));
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
}
