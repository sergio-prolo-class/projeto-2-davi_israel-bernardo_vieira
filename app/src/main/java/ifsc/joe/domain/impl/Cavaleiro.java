package ifsc.joe.domain.impl;

import ifsc.joe.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Cavaleiro extends Personagem {
  public static final String NOME_IMAGEM = "Cavaleiro";

  public Cavaleiro(int x, int y) {
    super(x, y, false, null);
  }

  // Desenha o Cavaleiro
  @Override
  public void desenhar(Graphics g, JPanel painel) {
    // verificando qual imagem carregar
    this.icone = this.carregarImagem(NOME_IMAGEM + (atacando ? "2" : ""));
    // desenhando de fato a imagem no pai
    g.drawImage(this.icone, this.posX, this.posY, painel);
  }

  // Método para atacar
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
        getClass().getClassLoader().getResource("./" + imagem + ".png"))).getImage();
  }

}
