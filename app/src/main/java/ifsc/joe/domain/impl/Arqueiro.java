package ifsc.joe.domain.impl;
import ifsc.joe.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Arqueiro extends Personagem{
    public static final String NOME_IMAGEM = "Arqueiro";

    public Arqueiro(int x, int y, boolean atacando, Image icone){
        super(x, y, atacando, icone);
    }

    /**
     * Desenhando o Arqueiro, nas coordenadas X e Y, com a imagem 'icone'
     * no JPanel 'pai'
     *
     * @param g objeto do JPanel que será usado para desenhar o Aldeão
     */
    public void desenhar(Graphics g, JPanel painel) {
        // verificando qual imagem carregar
        this.icone = this.carregarImagem(NOME_IMAGEM + (atacando ? "2" : ""));
        // desenhando de fato a imagem no pai
        g.drawImage(this.icone, this.posX, this.posY, painel);
    }
    
    @Override
    public void atacar() {
        this.atacando = !this.atacando;
    }

    /**
     * Metodo auxiliar para carregar uma imagem do disco
     *
     * @param imagem Caminho da imagem
     * @return Retorna um objeto Image
     */
    private Image carregarImagem(String imagem) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("./"+imagem+".png")
        )).getImage();
    }
}
