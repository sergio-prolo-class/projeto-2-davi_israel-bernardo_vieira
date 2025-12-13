# Java of Empires 🏰⚔️

Projeto desenvolvido para a disciplina de **Programação Orientada a Objetos (POO)** do IFSC – Campus São José, ministrada pelo **Prof. Sergio Prolo**.

O objetivo do projeto é aplicar, na prática, os principais conceitos de **POO em Java**, como **herança**, **polimorfismo**, **interfaces**, **organização em pacotes** e **interface gráfica com Swing**, em um jogo inspirado em *Age of Empires*.

---

## 🎯 Objetivos do Projeto

* Aplicar **herança e polimorfismo** através de uma hierarquia de personagens
* Utilizar **interfaces** para herança múltipla de tipo
* Desenvolver uma arquitetura **modular e escalável**
* Integrar **interface gráfica com Swing**
* Implementar funcionalidades escolhidas a partir da **Árvore de Requisitos**

---

## 🕹️ Funcionamento Atual do Projeto

O projeto possui uma base funcional contendo:

* Criação de personagens do tipo **Aldeão**, **Arqueiro** e **Cavaleiro**
* Interface gráfica com **Swing (JFrame + JPanel)**
* Movimentação dos personagens por botões direcionais
* Animação visual de ataque
* Respeito aos limites da tela
* Organização do código seguindo boas práticas de pacotes

---

## 🧱 Estrutura do Projeto

```
ifsc/
 └── joe/
     ├── App.java                  # Classe principal
     ├── domain/
     │   └── impl/
     │       ├── Personagem.java   # Classe base (superclasse)
     │       ├── Aldeao.java
     │       ├── Arqueiro.java
     │       └── Cavaleiro.java
     ├── enums/
     │   └── Direcao.java          # Enum para movimentação
     ├── interfaces/
     │   ├── Guerreiro.java
     │   ├── Coletador.java
     │   └── ComMontaria.java
     └── ui/
         ├── JanelaJogo.java
         ├── PainelControles.java
         └── Tela.java
```

---

## 🧬 Herança

A herança é aplicada através da classe abstrata **Personagem**, que define atributos e comportamentos comuns a todos os personagens do jogo.

```java
public abstract class Personagem {
    protected int vida;
    protected int ataque;
    protected int x, y;

    public abstract void atacar();
}
```

As classes **Aldeao**, **Arqueiro** e **Cavaleiro** herdam de `Personagem`:

```java
public class Arqueiro extends Personagem implements Guerreiro {
    @Override
    public void atacar() {
        System.out.println("Arqueiro atacando à distância");
    }
}
```

---

## 🔄 Polimorfismo

O polimorfismo é aplicado ao tratar diferentes tipos de personagens de forma genérica através da classe base `Personagem`.

Exemplo: uma coleção que armazena **todos os personagens**, independentemente do tipo.

```java
List<Personagem> personagens = new ArrayList<>();

personagens.add(new Aldeao());
personagens.add(new Arqueiro());
personagens.add(new Cavaleiro());

for (Personagem p : personagens) {
    p.atacar(); // comportamento diferente para cada tipo
}
```

Cada personagem executa sua própria implementação do método `atacar()`, demonstrando claramente o **polimorfismo em tempo de execução**.

---

## 🧩 Interfaces Utilizadas

O projeto utiliza interfaces para representar habilidades específicas:

* `Guerreiro` → personagens que atacam
* `Coletador` → personagens que coletam recursos
* `ComMontaria` → personagens que utilizam montaria

Exemplo:

```java
public interface Guerreiro {
    void atacar();
}
```

```java
public class Cavaleiro extends Personagem implements Guerreiro, ComMontaria {
    @Override
    public void atacar() {
        System.out.println("Cavaleiro atacando com espada");
    }
}
```

---

## ▶️ Como Executar o Projeto

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/java-of-empires.git
```

2. Abra o projeto no **IntelliJ IDEA**
3. Verifique se o plugin **Swing UI Designer** está habilitado
4. Execute a classe `App.java`

---

## 🌳 Árvore de Requisitos

As funcionalidades implementadas foram escolhidas a partir da **Árvore de Requisitos**, respeitando dependências e atingindo a pontuação de 34 Pontos.

Exemplos de requisitos atendidos:

* ✔ Implementação de Arqueiro e Cavaleiro (obrigatório)
* ✔  ⚔️ Sistema de Combate ⚔️ ( Ataque Básico , Sistema de Morte , Alcance Variável )
* ✔  🎮 Controles Avançados 🎮 ( Filtros por Tipo, Controle de Montaria, Atalhos do Teclado )
* ✔ 🖥️ Interface do Usuário 🖥️ ( Barra de Vida e Efeitos Sonoros )

---

## 🧠 Decisões de Design

* Uso de **classe base abstrata** para evitar duplicação de código
* Uso de **interfaces** para permitir flexibilidade e múltiplos comportamentos
* Separação clara entre **domínio**, **interface gráfica** e **lógica do jogo**
* Organização em pacotes seguindo boas práticas Java

---

## 📁 .gitignore

Arquivo `.gitignore` utilizado no projeto:

```gitignore
# Java
*.class
*.jar
*.war
*.ear

# IntelliJ IDEA
.idea/
*.iml
out/

# Logs
*.log

# Sistema operacional
.DS_Store
Thumbs.db
```

---

## 📚 Aprendizados

Durante o desenvolvimento do projeto foi possível consolidar:

* Conceitos fundamentais de **POO em Java**
* Uso prático de **herança, polimorfismo e interfaces**
* Organização de projetos maiores
* Integração entre lógica e interface gráfica

---

## 👤 Autor

* **Davi Israel Quirino**
* **Bernardo Vieira**

Projeto desenvolvido exclusivamente para fins acadêmicos, conforme as regras da disciplina.
