package ifsc.joe.ui;

import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Personagem;
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Tela extends JPanel {

    private final Set<Personagem> Personagem;

    public Tela() {

        // TODO preciso ser melhorado

        this.setBackground(Color.white);
        this.aldeoes = new HashSet<>();

    }

    /**
     * Method que invocado sempre que o JPanel precisa ser resenhado.
     * 
     * @param g Graphics componente de java.awt
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // TODO preciso ser melhorado

        // percorrendo a lista de aldeões e pedindo para cada um se desenhar na tela
        this.aldeoes.forEach(aldeao -> aldeao.desenhar(g, this));

        // liberando o contexto gráfico
        g.dispose();
    }

    /**
     * Cria um aldeao nas coordenadas X e Y, desenha-o neste JPanel
     * e adiciona o mesmo na lista de aldeoes
     *
     * @param x coordenada X
     * @param y coordenada Y
     */
    public void criarAldeao(int x, int y) {
        Aldeao a = new Aldeao(x, y);
        a.desenhar(super.getGraphics(), this);
        this.aldeoes.add(a);
    }

    /**
     * Atualiza as coordenadas X ou Y de todos os aldeoes
     *
     * @param direcao direcao para movimentar
     */
    public void movimentarAldeoes(Direcao direcao) {
        // TODO preciso ser melhorado

        this.aldeoes.forEach(aldeao -> aldeao.mover(direcao, this.getWidth(), this.getHeight()));

        // Depois que as coordenadas foram atualizadas é necessário repintar o JPanel
        this.repaint();
    }

    /**
     * Altera o estado do aldeão de atacando para não atacando e vice-versa
     */
    public void atacarAldeoes() {

        // TODO preciso ser melhorado

        // Percorrendo a lista de aldeões e pedindo para todos atacarem
        this.aldeoes.forEach(Aldeao::atacar);

        // Fazendo o JPanel ser redesenhado
        this.repaint();
    }
}
