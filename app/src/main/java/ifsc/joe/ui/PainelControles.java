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
    }

    // ------------------------- CONFIGURA LISTENERS -------------------------

    private void configurarListeners() {
        configurarBotoesMovimento();
        configurarBotoesCriacao();
        configurarBotaoAtaque();
        configurarBotaoMontaria();
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

    private void alternarMontaria() {
        Personagem p = getPersonagemSelecionado();
        if (p instanceof ComMontaria montaria) {
            montaria.alternarMontado();
            tela.repaint();
        } else {
            System.out.println("Este personagem não possui montaria.");
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
        Personagem personagem = getPersonagemSelecionado();

        // É importante verificar se 'personagem' é null (se nada estiver selecionado)
        if (personagem instanceof Guerreiro) {

            // Polimorfismo: chama o atacar() correto para Arqueiro ou Cavaleiro
            ((Guerreiro) personagem).atacar();

        } else {
            // Feedback para o usuário: nada selecionado ou o alvo (ex: Aldeao) não pode atacar
            System.out.println("Ação inválida: Nenhum atacante válido selecionado.");
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
        configurarListeners();
    }
}
