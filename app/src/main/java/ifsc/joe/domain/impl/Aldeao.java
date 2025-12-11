package ifsc.joe.domain.impl;

import ifsc.joe.Interfaces.Coletador;
import ifsc.joe.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Aldeao extends Personagem implements Coletador {
    public static final String NOME_IMAGEM = "aldeao";

    public Aldeao(int x, int y) {
        // vida, ataque, defesa
        super(x, y, false, null, 100, 0, 5);
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

}
