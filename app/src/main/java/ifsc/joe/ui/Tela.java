package ifsc.joe.ui;

import ifsc.joe.Interfaces.ComMontaria;
import ifsc.joe.Interfaces.Guerreiro;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Personagem;
import ifsc.joe.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;

    public Tela() {

        // TODO preciso ser melhorado

        this.setBackground(Color.white);
        this.personagens = new HashSet<>();

        //Integração com o teclado:
        setFocusable(true);
        requestFocusInWindow();
        configurarAtalhos();

        //Captura os cliques do mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                selecionarPersonagem(e.getX(), e.getY());
            }
        });
    }

    //Configura as teclas correspondentes do teclado:
    private void configurarAtalhos() {

        // Movimentação (WASD)
        bind("W", "moverCima",    () -> moverSelecionado(Direcao.CIMA));
        bind("S", "moverBaixo",   () -> moverSelecionado(Direcao.BAIXO));
        bind("A", "moverEsq",     () -> moverSelecionado(Direcao.ESQUERDA));
        bind("D", "moverDir",     () -> moverSelecionado(Direcao.DIREITA));

        // Setas
        bind("UP",    "moverCima2",  () -> moverSelecionado(Direcao.CIMA));
        bind("DOWN",  "moverBaixo2", () -> moverSelecionado(Direcao.BAIXO));
        bind("LEFT",  "moverEsq2",   () -> moverSelecionado(Direcao.ESQUERDA));
        bind("RIGHT", "moverDir2",   () -> moverSelecionado(Direcao.DIREITA));

        // Atacar (barra de espaço)
        bind("SPACE", "atacar", () -> {
            if (personagemAtivo instanceof Guerreiro g)
                g.atacar();
            repaint();
        });

        // Alternar montado/desmontado
        bind("M", "montaria", () -> {
            if (personagemAtivo instanceof ComMontaria cm)
                cm.alternarMontado();
            repaint();
        });
    }

    private void bind(String tecla, String nome, Runnable evento) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(tecla), nome);
        getActionMap().put(nome, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evento.run();
            }
        });
    }

    private void moverSelecionado(Direcao dir) {
        if (personagemAtivo != null)
            personagemAtivo.mover(dir, getWidth(), getHeight());

        repaint();
    }


    private void selecionarPersonagem(int x, int y) {
        // converte para lista para poder inverter a ordem
        List<Personagem> lista = new ArrayList<>(personagens);
        Collections.reverse(lista);

        for (Personagem p : lista) {
            if (p.foiClicado(x, y)) {
                personagemAtivo = p;
                System.out.println("[Selecionado] "
                        + p.getClass().getSimpleName()
                        + " hash=" + System.identityHashCode(p)
                        + " pos=(" + getX() + "," + getY() + ")");
                repaint();
                return;
            }
        }

        // nenhum atingido
        personagemAtivo = null;
        System.out.println("[Selecionado] nenhum personagem");
    }

    //Guarda o personagem ativo/selecionado
    private Personagem personagemAtivo;

    //Usa no painel de controles para saber quem está ativo
    public Personagem getPersonagemAtivo() {
        return this.personagemAtivo;
    }
    //Serve par quando o usuário clicar em um personagem
    public void setPersonagemAtivo(Personagem personagem) {
        this.personagemAtivo = personagem;
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
        this.personagens.stream()
                .filter(p -> p instanceof Guerreiro)
                .forEach(p -> ((Guerreiro) p).atacar());
        repaint();
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
        this.personagens.stream()
                .filter(p -> tipo.isInstance(p))      // só pega do tipo escolhido
                .filter(p -> p instanceof Guerreiro)  // só guerreiros atacam
                .forEach(p -> ((Guerreiro) p).atacar());

        repaint();
    }
}
