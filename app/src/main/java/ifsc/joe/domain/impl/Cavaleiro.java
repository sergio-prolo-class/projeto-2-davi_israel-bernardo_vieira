package ifsc.joe.domain.impl;

import ifsc.joe.Interfaces.ComMontaria;
import ifsc.joe.Interfaces.Guerreiro;
import ifsc.joe.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Cavaleiro extends Personagem implements Guerreiro, ComMontaria {
  public static final String NOME_IMAGEM = "Cavaleiro";
  private boolean montado = true;

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

  //Montado no cavalo:
    @Override
    public void alternarMontado(){
      montado = !montado;
    }

    @Override
    public boolean isMontado() {
      return montado;
    }

    //Movendo com a montaria:

    @Override
    public void mover(Direcao dir, int w, int h) {
        int velocidade = montado ? 20 : 10;  // mais rápido montado

        switch (dir) {
            case CIMA    -> posY -= velocidade;
            case BAIXO   -> posY += velocidade;
            case ESQUERDA-> posX -= velocidade;
            case DIREITA -> posX += velocidade;
        }

        // mantém dentro dos limites
        posX = Math.max(0, Math.min(w - 50, posX));
        posY = Math.max(0, Math.min(h - 50, posY));
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
