package ifsc.joe.domain.impl;

import java.awt.Image;

import ifsc.joe.enums.Direcao;

public abstract class Personagem {
  protected int posX, posY;
  protected boolean atacando;
  protected Image icone;

  public Personagem(int x, int y, boolean atacando, Image icone) {
    this.posX = x;
    this.posY = y;
    this.atacando = atacando;
    this.icone = icone;
  }

  /**
   * Atualiza as coordenadas X e Y do personagem
   *
   * @param direcao indica a direcao a ir.
   */
  public final void mover(Direcao direcao, int maxLargura, int maxAltura) {
    switch (direcao) {
      case CIMA -> this.posY -= 10;
      case BAIXO -> this.posY += 10;
      case ESQUERDA -> this.posX -= 10;
      case DIREITA -> this.posX += 10;
    }

    // Não deixa a imagem ser desenhada fora dos limites do JPanel pai
    this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
    this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
  }

  public void atacar() {
    this.atacando = !this.atacando;
  }

}
