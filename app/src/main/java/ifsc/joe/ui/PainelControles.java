package ifsc.joe.ui;

import ifsc.joe.Interfaces.ComMontaria;
import ifsc.joe.Interfaces.Guerreiro;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro; // quando criarmos
import ifsc.joe.domain.impl.Personagem;
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
    private JButton bMontar;
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
        // A chamada para configurarListeners() foi movida para cá
        // para garantir que os componentes da UI (botões) já tenham sido inicializados
        // pelo código gerado pelo GUI Designer.
        SwingUtilities.invokeLater(this::configurarListeners);
    }

    // ------------------------- CONFIGURA LISTENERS -------------------------

    private void configurarListeners() {
        configurarBotoesMovimento();
        configurarBotoesCriacao();
        configurarBotaoAtaque();
        configurarBotaoMontaria();
        configurarControlesTeclado();
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

    // ------------------------ MONTARIA -------------------------

    private void configurarBotaoMontaria() {
        bMontar.addActionListener(e -> alternarMontaria());
    }

    // ------------------------ CONTROLES POR TECLADO -------------------------

    private void configurarControlesTeclado() {
        // A Tela já tem KeyBindings para movimento, ataque e montaria.
        // Aqui vamos adicionar a criação de personagens e a troca de filtro.

        // Criação de Personagens (1, 2, 3) - CORRIGIDO para usar o teclado numérico
        getTela().bind("NUMPAD1", "criaAldeao", () -> {
            int[] pos = gerarPosicaoAleatoria();
            getTela().adicionarPersonagem(new Aldeao(pos[0], pos[1]));
        });

        getTela().bind("NUMPAD2", "criaArqueiro", () -> {
            int[] pos = gerarPosicaoAleatoria();
            getTela().adicionarPersonagem(new Arqueiro(pos[0], pos[1]));
        });

        getTela().bind("NUMPAD3", "criaCavaleiro", () -> {
            int[] pos = gerarPosicaoAleatoria();
            getTela().adicionarPersonagem(new Cavaleiro(pos[0], pos[1]));
        });

        // Troca de Filtro (Tab)
        getTela().bind("TAB", "trocaFiltro", this::alternarFiltro);
    }

    private void alternarFiltro() {
        if (todosRadioButton.isSelected()) {
            aldeaoRadioButton.setSelected(true);
        } else if (aldeaoRadioButton.isSelected()) {
            arqueiroRadioButton.setSelected(true);
        } else if (arqueiroRadioButton.isSelected()) {
            cavaleiroRadioButton.setSelected(true);
        } else {
            todosRadioButton.setSelected(true);
        }
    }

    private void alternarMontaria() {
        Class<?> tipo = getTipoSelecionado();

        if (tipo == null) {
            getTela().alternarMontariaTodos();
        } else {
            getTela().alternarMontariaPorTipo(tipo);
        }
    }

    // ------------------------ ATAQUE -------------------------

    private void configurarBotaoAtaque() {
        atacarButton.addActionListener(e -> atacarFiltrado());
    }

    private Personagem getPersonagemSelecionado() {
        // Apenas obtém o objeto que a Tela está rastreando
        return getTela().getPersonagemAtivo();
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
