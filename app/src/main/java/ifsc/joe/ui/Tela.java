package ifsc.joe.ui;

import ifsc.joe.Interfaces.ComMontaria;
import ifsc.joe.Interfaces.Guerreiro;
import ifsc.joe.domain.impl.Personagem;
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;
    private Personagem personagemAtivo;

    // Botão visual para alternar montaria
    private final JButton montariaButton;

    public Tela() {

        this.setBackground(Color.white);
        this.personagens = new HashSet<>();

        // Colocamos layout nulo para posicionar facilmente o botão de controle.
        // Se você preferir outro layout (BorderLayout etc.) pode adaptar.
        setLayout(null);

        // Cria e configura o botão de montaria
        montariaButton = criarBotaoMontaria();
        montariaButton.setBounds(10, 10, 160, 36);
        add(montariaButton);

        // Garantir foco sempre que mostrado
        setFocusable(true);
        requestFocusInWindow();
        addHierarchyListener(e -> {
            if (isShowing())
                requestFocusInWindow();
        });

        configurarAtalhos();

        // Captura clique do mouse -> seleção de personagem
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // clique no próprio botão não deve deselecionar foco do painel
                if (montariaButton.getBounds().contains(e.getPoint())) {
                    // deixa o botão tratar seu clique
                    return;
                }
                selecionarPersonagem(e.getX(), e.getY());
            }
        });

        // Ao clicar no painel, pede foco para receber teclas
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
            }
        });
    }

    // =============================
    // CONFIGURAÇÃO DE ATALHOS
    // =============================
    private void configurarAtalhos() {

        // Observação: usamos "pressed X" para garantir que o KeyStroke detecte a tecla,
        // isso corrige casos em que apenas "X" não estava disparando.

        // Movimentação WASD
        bind("W", "moverCima", () -> moverSelecionado(Direcao.CIMA));
        bind("S", "moverBaixo", () -> moverSelecionado(Direcao.BAIXO));
        bind("A", "moverEsq", () -> moverSelecionado(Direcao.ESQUERDA));
        bind("D", "moverDir", () -> moverSelecionado(Direcao.DIREITA));

        // Movimento com setas
        bind("UP", "moverCima2", () -> moverSelecionado(Direcao.CIMA));
        bind("DOWN", "moverBaixo2", () -> moverSelecionado(Direcao.BAIXO));
        bind("LEFT", "moverEsq2", () -> moverSelecionado(Direcao.ESQUERDA));
        bind("RIGHT", "moverDir2", () -> moverSelecionado(Direcao.DIREITA));

        // Atacar (barra de espaço)
        bind("SPACE", "atacar", () -> {
            if (personagemAtivo instanceof Guerreiro g) {
                g.atacar();
                System.out.println("[Atacar] " + personagemAtivo.getClass().getSimpleName());
            }
            repaint();
        });

        // Alternar montado/desmontado (tecla M)
        bind("M", "montaria", () -> {
            if (personagemAtivo instanceof ComMontaria cm) {
                cm.alternarMontado();
                System.out.println("[Montaria] " + personagemAtivo.getClass().getSimpleName()
                        + " -> " + (cm.isMontado() ? "Montado" : "Desmontado"));
                atualizarBotaoMontaria();
                repaint();
            }
        });
    }

    /**
     * Bind seguro: cria KeyStroke com "pressed <tecla>".
     * Ex.: tecla = "W", "UP", "SPACE", "M"
     */
    public void bind(String tecla, String nome, Runnable evento) {
        KeyStroke ks = KeyStroke.getKeyStroke("pressed " + tecla);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(ks, nome);
        getActionMap().put(nome, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evento.run();
            }
        });
    }

    private void moverSelecionado(Direcao dir) {
        if (personagemAtivo != null) {
            personagemAtivo.mover(dir, getWidth(), getHeight());
            repaint();
        }
    }

    // =============================
    // SELEÇÃO DE PERSONAGENS
    // =============================
    private void selecionarPersonagem(int x, int y) {

        List<Personagem> lista = new ArrayList<>(personagens);
        Collections.reverse(lista);

        for (Personagem p : lista) {
            if (p.foiClicado(x, y)) {
                personagemAtivo = p;
                System.out.println("[Selecionado] " + p.getClass().getSimpleName());
                atualizarBotaoMontaria();
                repaint();
                return;
            }
        }

        personagemAtivo = null;
        System.out.println("[Selecionado] nenhum personagem");
        atualizarBotaoMontaria();
    }

    // =============================
    // DESENHO
    // =============================
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.personagens.forEach(p -> p.desenhar(g, this));
    }

    // =============================
    // MÉTODOS EXTERNOS
    // =============================
    public void adicionarPersonagem(Personagem p) {
        this.personagens.add(p);
        // se adicionar e for o primeiro, não seleciona automaticamente,
        // mas atualizamos estado do botão
        atualizarBotaoMontaria();
        repaint();
    }

    public Personagem getPersonagemAtivo() {
        return personagemAtivo;
    }

    public void setPersonagemAtivo(Personagem personagem) {
        this.personagemAtivo = personagem;
        atualizarBotaoMontaria();
    }

    public List<Personagem> getPersonagens() {
        return new ArrayList<>(personagens);
    }

    // =============================
    // FUNÇÕES DE MONTARIA
    // =============================
    public void alternarMontariaSelecionado() {
        if (personagemAtivo instanceof ComMontaria cm) {
            cm.alternarMontado();
            atualizarBotaoMontaria();
            repaint();
        }
    }

    public void alternarMontariaTodos() {
        personagens.stream()
                .filter(p -> p instanceof ComMontaria)
                .forEach(p -> ((ComMontaria) p).alternarMontado());
        atualizarBotaoMontaria();
        repaint();
    }

    public void alternarMontariaPorTipo(Class<?> tipo) {
        personagens.stream()
                .filter(tipo::isInstance)
                .filter(p -> p instanceof ComMontaria)
                .forEach(p -> ((ComMontaria) p).alternarMontado());
        atualizarBotaoMontaria();
        repaint();
    }

    // =============================
    // ATAQUES
    // =============================
    public void atacarTodos() {
        personagens.stream()
                .filter(p -> p instanceof Guerreiro)
                .forEach(p -> ((Guerreiro) p).atacarTodosProximos(getPersonagens()));

        personagens.removeIf(p -> !p.estaVivo());
        repaint();
    }

    public void atacarPersonagem(Class<?> tipo) {
        personagens.stream()
                .filter(tipo::isInstance)
                .filter(p -> p instanceof Guerreiro)
                .forEach(p -> ((Guerreiro) p).atacarTodosProximos(getPersonagens()));

        personagens.removeIf(p -> !p.estaVivo());
        repaint();
    }

    // =============================
    // MOVIMENTAÇÃO EM GRUPO
    // =============================
    public void moverTodosPersonagens(Direcao direcao) {
        personagens.forEach(p -> p.mover(direcao, getWidth(), getHeight()));
        repaint();
    }

    public void moverPersonagem(Class<?> tipo, Direcao direcao) {
        personagens.stream()
                .filter(tipo::isInstance)
                .forEach(p -> p.mover(direcao, getWidth(), getHeight()));
        repaint();
    }

    // =============================
    // BOTÃO DE MONTARIA (UI)
    // =============================
    private JButton criarBotaoMontaria() {
        JButton btn = new JButton("🏇 Montar/Desmontar");
        btn.setFocusable(false); // importante: não roubar foco do painel
        btn.addActionListener(e -> {
            // se existir personagem ativo com montaria, alterna
            if (personagemAtivo instanceof ComMontaria cm) {
                cm.alternarMontado();
                atualizarBotaoMontaria();
                repaint();
            } else {
                // feedback rápido
                Toolkit.getDefaultToolkit().beep();
            }
            requestFocusInWindow(); // volta foco ao painel para teclas funcionarem
        });
        return btn;
    }

    /**
     * Atualiza texto/estado do botão conforme o personagem selecionado.
     */
    private void atualizarBotaoMontaria() {
        if (personagemAtivo == null) {
            montariaButton.setText("🏇 Montar/Desmontar (nenhum)");
            montariaButton.setEnabled(false);
        } else if (personagemAtivo instanceof ComMontaria cm) {
            montariaButton.setEnabled(true);
            String estado = cm.isMontado() ? "Montado" : "Desmontado";
            montariaButton.setText("🏇 " + estado + " (M para alternar)");
        } else {
            montariaButton.setEnabled(false);
            montariaButton.setText("🏇 Não possui montaria");
        }
    }

    /**
     * Exposição pública do botão caso você queira reposicioná-lo em outro
     * container.
     */
    public JButton getMontariaButton() {
        return montariaButton;
    }
}
