package ifsc.joe.ui;

import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro; // quando criarmos
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.util.Random;

public class PainelControles {

    private final Random sorteio;
    private Tela tela;

    // Componentes da interface
    private JPanel painelPrincipal;
    private JPanel painelTela;
    private JPanel painelLateral;
    private JButton bCriaAldeao;
    private JButton bCriaArqueiro;
    private JButton bCriaCavaleiro;
    private JRadioButton todosRadioButton;
    private JRadioButton aldeaoRadioButton;
    private JRadioButton arqueiroRadioButton;
    private JRadioButton cavaleiroRadioButton;
    private JButton atacarButton;
    private JButton buttonCima;
    private JButton buttonEsquerda;
    private JButton buttonBaixo;
    private JButton buttonDireita;
    private JLabel logo;

    public PainelControles() {
        this.sorteio = new Random();
        configurarListeners();
    }

    // ------------------------- CONFIGURA LISTENERS -------------------------

    private void configurarListeners() {
        configurarBotoesMovimento();
        configurarBotoesCriacao();
        configurarBotaoAtaque();
    }

    // ------------------------ MOVIMENTAÇÃO -------------------------

    private void configurarBotoesMovimento() {
        buttonCima.addActionListener(e -> moverFiltrado(Direcao.CIMA));
        buttonBaixo.addActionListener(e -> moverFiltrado(Direcao.BAIXO));
        buttonEsquerda.addActionListener(e -> moverFiltrado(Direcao.ESQUERDA));
        buttonDireita.addActionListener(e -> moverFiltrado(Direcao.DIREITA));
    }

    private void moverFiltrado(Direcao direcao) {
        Class<?> tipo = getTipoSelecionado();

        if (tipo == null) {
            getTela().moverTodosPersonagens(direcao);
        } else {
            getTela().moverPersonagem(tipo, direcao);
        }
    }

    // ------------------------ ATAQUE -------------------------

    private void configurarBotaoAtaque() {
        atacarButton.addActionListener(e -> atacarFiltrado());
    }

    private void atacarFiltrado() {
        Class<?> tipo = getTipoSelecionado();

        if (tipo == null) {
            getTela().atacarTodos();
        } else {
            getTela().atacarPersonagem(tipo);
        }
    }

    // ------------------------ CRIAÇÃO DE PERSONAGENS -------------------------

    private void configurarBotoesCriacao() {

        bCriaAldeao.addActionListener(e -> {
            int[] pos = gerarPosicaoAleatoria();
            getTela().adicionarPersonagem(new Aldeao(pos[0], pos[1]));
        });

        bCriaArqueiro.addActionListener(e -> {
            int[] pos = gerarPosicaoAleatoria();
            getTela().adicionarPersonagem(new Arqueiro(pos[0], pos[1]));
        });

        bCriaCavaleiro.addActionListener(e -> {
            int[] pos = gerarPosicaoAleatoria();
            getTela().adicionarPersonagem(new Cavaleiro(pos[0], pos[1]));
        });
    }

    private int[] gerarPosicaoAleatoria() {
        final int PADDING = 50;
        int posX = sorteio.nextInt(painelTela.getWidth() - PADDING);
        int posY = sorteio.nextInt(painelTela.getHeight() - PADDING);
        return new int[] { posX, posY };
    }

    // ------------------------ UTILITÁRIOS -------------------------

    private Class<?> getTipoSelecionado() {
        if (aldeaoRadioButton.isSelected())
            return Aldeao.class;
        if (arqueiroRadioButton.isSelected())
            return Arqueiro.class;
        if (cavaleiroRadioButton.isSelected())
            return Cavaleiro.class;
        return null;
    }

    private Tela getTela() {
        if (tela == null) {
            tela = (Tela) painelTela;
        }
        return tela;
    }

    public JPanel getPainelPrincipal() {
        return painelPrincipal;
    }

    private void createUIComponents() {
        this.painelTela = new Tela();
    }
}
