package ifsc.joe.ui;

import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Personagem;
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;

    public Tela() {

        // TODO preciso ser melhorado

        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
    }

    // Invoca sempre que precisa redesenhar o tabuleiro
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO preciso ser melhorado

        // percorrendo a lista de personagens e pedindo para cada um se desenhar na tela
        this.personagens.forEach(p -> p.desenhar(g, this));

        // liberando o contexto gráfico
        g.dispose();
    }

    // Cria um Personagem
    public void adicionarPersonagem(Personagem p) {
        this.personagens.add(p);
        this.repaint();
    }

    // Move TODOS os personagens
    public void moverTodosPersonagens(Direcao direcao) {
        // TODO preciso ser melhorado
        this.personagens.forEach(p -> p.mover(direcao, getWidth(), getHeight()));
        this.repaint();
    }

    // Ativa o estado de ataque para TODOS os personagens
    public void atacarTodos() {
        // TODO preciso ser melhorado
        this.personagens.forEach(Personagem::atacar);
        this.repaint();
    }

    // Move um TIPO de personagem
    public void moverPersonagem(Class<?> tipo, Direcao direcao) {
        personagens.stream()
                .filter(p -> tipo.isInstance(p))
                .forEach(p -> p.mover(direcao, getWidth(), getHeight()));
        repaint();
    }

    // Ataca somente um TIPO de personagem
    public void atacarPersonagem(Class<?> tipo) {
        personagens.stream()
                .filter(p -> tipo.isInstance(p))
                .forEach(Personagem::atacar);
        repaint();
    }
}
