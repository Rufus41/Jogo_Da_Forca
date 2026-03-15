package tp1.ParteD_library;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Classe responsável pela lógica de uma ronda do jogo da Forca.
 *
 * <p>Gere o ciclo de jogo turno a turno, incluindo a leitura de ações do
 * utilizador, a revelação de letras, a adivinhação da palavra completa,
 * o uso de power-ups e o cálculo da pontuação final.</p>
 *
 * @see Palavra
 * @see Forca
 * @see Loja
 * @see HistoricoRonda
 */
public class Jogo {

  /** Gestor de palavras utilizado para sortear a palavra de cada ronda. */
  private final GestorPalavras gestorPalavras;

  /** Scanner utilizado para leitura da entrada do utilizador. */
  private final Scanner scanner;

  /** Gerador de números aleatórios utilizado para dicas e sorteio de palavras. */
  private final Random random = new Random();

  /**
   * Constrói um novo {@code Jogo} com o gestor de palavras e o scanner fornecidos.
   *
   * @param gestorPalavras o {@link GestorPalavras} utilizado para sortear palavras
   * @param scanner        o {@link Scanner} utilizado para leitura da entrada do utilizador
   */
  public Jogo(GestorPalavras gestorPalavras, Scanner scanner) {
    this.gestorPalavras = gestorPalavras;
    this.scanner = scanner;
  }

  /**
   * Executa uma ronda completa do jogo da Forca.
   *
   * <p>Sorteia uma palavra da categoria indicada, inicializa a forca com o número
   * de vidas definido pela dificuldade e corre o ciclo de jogo turno a turno.
   * Em cada turno, o utilizador pode:</p>
   * <ul>
   *   <li>{@code L} — tentar uma letra</li>
   *   <li>{@code P} — adivinhar a palavra completa (derrota imediata se errar)</li>
   *   <li>{@code 1} — usar uma Dica</li>
   *   <li>{@code 2} — ativar Congelar Vida</li>
   *   <li>{@code 3} — usar Revelar Vogais</li>
   *   <li>{@code 4} — ativar Palavra Fácil para a próxima ronda</li>
   * </ul>
   *
   * <p>A ronda termina quando a palavra é descoberta, o jogador fica sem vidas,
   * ou o jogador erra na adivinhação da palavra completa.</p>
   *
   * @param categoria   a categoria da palavra a sortear
   * @param dificuldade o nome da dificuldade escolhida
   * @param vidas       o número de vidas iniciais
   * @param valorBase   o valor base de pontuação por letra correta
   * @param loja        a {@link Loja} utilizada para consumir e ativar power-ups
   * @return um {@link HistoricoRonda} com os dados da ronda concluída
   */
  public HistoricoRonda jogar(String categoria, String dificuldade, int vidas, int valorBase, Loja loja) {
    Palavra palavra = gestorPalavras.sortearPalavra(categoria, loja.consumirPalavraFacilAtiva());
    Forca forca = new Forca(vidas);
    Set<Character> letrasTentadas = new LinkedHashSet<>();
    boolean acertouPalavraCompleta = false;
    boolean falhouPalavraCompleta = false;
    int letrasCorretas = 0;
    int turnoAtual = 1;
    double pontuacaoFinal = 0;

    while (!palavra.estaDescoberta() && !forca.perdeu() && !falhouPalavraCompleta) {
      double pontuacaoAtual = calcularPontuacaoBase(letrasCorretas, valorBase);
      mostrarEstado(palavra, forca, letrasTentadas, categoria, dificuldade, turnoAtual, pontuacaoAtual, loja);
      String acao = lerAcao();

      if ("L".equals(acao)) {
        char letra = lerLetra(letrasTentadas);
        letrasTentadas.add(letra);

        int letrasReveladas = palavra.revelarLetra(letra);

        if (letrasReveladas > 0) {
          letrasCorretas += letrasReveladas;
          pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
          System.out.println(ConsolaUtils.sucesso("Acertaste na letra."));
        } else {
          boolean perdeuVida = forca.registarErro();
          if (perdeuVida) {
            System.out.println(ConsolaUtils.erro("Letra errada."));
          } else {
            System.out.println(ConsolaUtils.sucesso("A vida congelada protegeu-te deste erro."));
          }
        }
      } else if ("P".equals(acao)) {
        String tentativa = lerPalavraCompleta();
        if (palavra.acertouPalavraCompleta(tentativa)) {
          acertouPalavraCompleta = true;
          palavra.revelarTudo();
          pontuacaoFinal = calcularPontuacaoPalavraCompleta(palavra.getNumeroDeLetras(), valorBase, turnoAtual);
          System.out.println(ConsolaUtils.sucesso("Acertaste na palavra completa."));
          break;
        }

        pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
        falhouPalavraCompleta = true;
        System.out.println(ConsolaUtils.erro("Palavra errada. Perdeste o jogo."));
      } else {
        int letrasReveladas = usarItem(acao, palavra, forca, loja);

        if (letrasReveladas > 0) {
          letrasCorretas += letrasReveladas;
          pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
        }

        if (palavra.estaDescoberta()) {
          pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
        }

        System.out.println();
        continue;
      }

      if (!palavra.estaDescoberta() && !forca.perdeu() && !falhouPalavraCompleta) {
        turnoAtual++;
      }

      System.out.println();
    }

    if (palavra.estaDescoberta() && !acertouPalavraCompleta) {
      pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
    }

    mostrarEstado(palavra, forca, letrasTentadas, categoria, dificuldade, turnoAtual, pontuacaoFinal, loja);

    String resultado;
    if (palavra.estaDescoberta() || acertouPalavraCompleta) {
      resultado = "Vitoria";
      System.out.println(ConsolaUtils.sucesso("Ganhaste. A palavra era " + palavra.getTextoOriginal() + "."));
    } else {
      resultado = "Derrota";
      System.out.println(ConsolaUtils.erro("Perdeste. A palavra era " + palavra.getTextoOriginal() + "."));
    }
    System.out.println(ConsolaUtils.neutro("Pontuação final: " + formatarPontuacao(pontuacaoFinal)));

    return new HistoricoRonda(
        palavra.getTextoOriginal(),
        categoria,
        dificuldade,
        resultado,
        pontuacaoFinal,
        turnoAtual);
  }

  /**
   * Apresenta o estado atual da ronda na consola.
   *
   * <p>Mostra o título, a categoria, a dificuldade, o turno atual, o desenho
   * da forca, o progresso da palavra, as letras já tentadas, os erros e
   * tentativas restantes, o estado da vida congelada, a pontuação atual
   * e os atalhos de ação disponíveis.</p>
   *
   * @param palavra         a {@link Palavra} em jogo
   * @param forca           a {@link Forca} com o estado atual de vidas e erros
   * @param letrasTentadas  o conjunto de letras já tentadas pelo utilizador
   * @param categoria       a categoria da palavra
   * @param dificuldade     o nome da dificuldade
   * @param turnoAtual      o número do turno atual
   * @param pontuacaoAtual  a pontuação acumulada até ao momento
   * @param loja            a {@link Loja} de onde são obtidas as quantidades dos power-ups
   */
  private void mostrarEstado(Palavra palavra, Forca forca, Set<Character> letrasTentadas,
      String categoria, String dificuldade, int turnoAtual, double pontuacaoAtual, Loja loja) {
    System.out.println(ConsolaUtils.titulo("JOGO DA FORCA"));
    System.out.println(ConsolaUtils.menu("Categoria: " + categoria));
    System.out.println(ConsolaUtils.menu("Dificuldade: " + dificuldade));
    System.out.println(ConsolaUtils.neutro("Turno: " + turnoAtual));
    System.out.println(forca.getDesenhoAtual());
    System.out.println(ConsolaUtils.menu("Palavra: " + palavra.getProgressoFormatado()));
    System.out.println(ConsolaUtils.neutro("Letras tentadas: " + formatarLetrasTentadas(letrasTentadas)));
    System.out.println(ConsolaUtils.neutro(
        "Erros: " + forca.getErros() + " | Tentativas restantes: " + forca.getTentativasRestantes()));
    System.out.println(ConsolaUtils.neutro("Vida congelada: " + (forca.temVidaCongelada() ? "ativa" : "não")));
    System.out.println(ConsolaUtils.neutro("Pontuação atual: " + formatarPontuacao(pontuacaoAtual)));
    System.out.println(ConsolaUtils.menu("[L] Tentar letra"));
    System.out.println(ConsolaUtils.menu("[P] Adivinhar palavra completa"));
    System.out.println(formatarAtalhoItem("1", "Dica", loja.getQuantidadeDicas()));
    System.out.println(formatarAtalhoItem("2", "Congelar Vida", loja.getQuantidadeCongelarVida()));
    System.out.println(formatarAtalhoItem("3", "Revelar Vogais", loja.getQuantidadeRevelarVogais()));
    System.out.println(formatarAtalhoItem("4", "Palavra Fácil", loja.getQuantidadePalavraFacil()));
    System.out.println();
  }

  /**
   * Calcula a pontuação base acumulada com base no número de letras corretas.
   *
   * @param letrasCorretas o número de letras corretamente reveladas até ao momento
   * @param valorBase      o valor base de pontuação por letra
   * @return a pontuação base calculada ({@code letrasCorretas * valorBase})
   */
  public static double calcularPontuacaoBase(int letrasCorretas, int valorBase) {
    return letrasCorretas * valorBase;
  }

  /**
   * Devolve o multiplicador de pontuação para adivinhação da palavra completa
   * com base no turno atual.
   *
   * <p>Os multiplicadores aplicados são:</p>
   * <ul>
   *   <li>Turno 1 — multiplicador 3.0x</li>
   *   <li>Turno 2 — multiplicador 2.0x</li>
   *   <li>Turno 3 — multiplicador 1.5x</li>
   *   <li>Turno 4 ou mais — multiplicador 1.0x</li>
   * </ul>
   *
   * @param turnoAtual o número do turno em que a palavra foi adivinhada
   * @return o multiplicador de pontuação correspondente
   */
  public static double getMultiplicador(int turnoAtual) {
    if (turnoAtual == 1) {
      return 3.0;
    }
    if (turnoAtual == 2) {
      return 2.0;
    }
    if (turnoAtual == 3) {
      return 1.5;
    }
    return 1.0;
  }

  /**
   * Calcula a pontuação obtida por adivinhar a palavra completa.
   *
   * <p>A pontuação é calculada como
   * {@code numeroLetras * valorBase * getMultiplicador(turnoAtual)}.</p>
   *
   * @param numeroLetras o número de letras da palavra
   * @param valorBase    o valor base de pontuação por letra
   * @param turnoAtual   o turno em que a palavra foi adivinhada
   * @return a pontuação calculada para a adivinhação da palavra completa
   */
  public static double calcularPontuacaoPalavraCompleta(int numeroLetras, int valorBase, int turnoAtual) {
    return numeroLetras * valorBase * getMultiplicador(turnoAtual);
  }

  /**
   * Lê e valida a ação escolhida pelo utilizador no turno atual.
   *
   * <p>Aceita as entradas {@code L}, {@code P} e os atalhos de power-up
   * {@code 1}, {@code 2}, {@code 3} e {@code 4}. Repete o pedido enquanto
   * a entrada não for válida.</p>
   *
   * @return a ação escolhida como {@code String} em maiúsculas
   */
  private String lerAcao() {
    while (true) {
      System.out.print(ConsolaUtils.menu("Escolhe uma ação: "));
      String input = scanner.nextLine().trim().toUpperCase();

      if ("L".equals(input) || "P".equals(input)
          || "1".equals(input) || "2".equals(input)
          || "3".equals(input) || "4".equals(input)) {
        return input;
      }

      System.out.println(ConsolaUtils.erro("Escolhe L, P ou um dos atalhos 1-4."));
    }
  }

  /**
   * Lê e valida uma letra introduzida pelo utilizador.
   *
   * <p>Rejeita entradas com mais de um caractere, entradas não alfabéticas e
   * letras que já tenham sido tentadas anteriormente. Repete o pedido em
   * todos estes casos.</p>
   *
   * @param letrasTentadas o conjunto de letras já tentadas, usado para validação
   * @return a letra válida introduzida, em maiúscula
   */
  private char lerLetra(Set<Character> letrasTentadas) {
    while (true) {
      System.out.print(ConsolaUtils.menu("Introduz uma letra: "));
      String input = scanner.nextLine().trim().toUpperCase();

      if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
        System.out.println(ConsolaUtils.erro("Introduz apenas uma letra válida."));
        continue;
      }

      char letra = input.charAt(0);

      if (letrasTentadas.contains(letra)) {
        System.out.println(ConsolaUtils.erro("Essa letra já foi tentada."));
        continue;
      }

      return letra;
    }
  }

  /**
   * Lê e valida a tentativa de palavra completa introduzida pelo utilizador.
   *
   * <p>Rejeita entradas vazias, repetindo o pedido até ser introduzido
   * um valor válido.</p>
   *
   * @return a palavra introduzida pelo utilizador, sem espaços iniciais ou finais
   */
  private String lerPalavraCompleta() {
    while (true) {
      System.out.print(ConsolaUtils.menu("Introduz a palavra completa: "));
      String input = scanner.nextLine().trim();

      if (!input.isEmpty()) {
        return input;
      }

      System.out.println(ConsolaUtils.erro("Introduz uma palavra válida."));
    }
  }

  /**
   * Formata o conjunto de letras tentadas para apresentação na consola.
   *
   * <p>As letras são separadas por espaços. Se o conjunto estiver vazio,
   * devolve {@code "-"}.</p>
   *
   * @param letrasTentadas o conjunto de letras já tentadas
   * @return uma {@code String} com as letras separadas por espaços,
   *         ou {@code "-"} se não existirem letras tentadas
   */
  private String formatarLetrasTentadas(Set<Character> letrasTentadas) {
    if (letrasTentadas.isEmpty()) {
      return "-";
    }

    StringBuilder sb = new StringBuilder();

    for (char letra : letrasTentadas) {
      if (sb.length() > 0) {
        sb.append(' ');
      }
      sb.append(letra);
    }

    return sb.toString();
  }

  /**
   * Formata um valor de pontuação para apresentação ao utilizador.
   *
   * <p>Se a pontuação for um número inteiro, devolve-o sem casas decimais.
   * Caso contrário, formata com uma casa decimal usando o ponto como separador
   * decimal (locale {@link Locale#US}).</p>
   *
   * @param pontuacao o valor de pontuação a formatar
   * @return a pontuação formatada como {@code String}
   */
  private String formatarPontuacao(double pontuacao) {
    if (pontuacao == Math.floor(pontuacao)) {
      return String.valueOf((int) pontuacao);
    }

    return String.format(Locale.US, "%.1f", pontuacao);
  }

  /**
   * Processa o uso de um power-up durante o turno atual.
   *
   * <p>Delega para o método de consumo correspondente na {@link Loja} e
   * aplica o efeito do power-up na {@link Palavra} ou na {@link Forca}:</p>
   * <ul>
   *   <li>{@code "1"} — Dica: revela uma letra oculta aleatória</li>
   *   <li>{@code "2"} — Congelar Vida: ativa a proteção contra o próximo erro</li>
   *   <li>{@code "3"} — Revelar Vogais: revela todas as vogais ainda ocultas</li>
   *   <li>{@code "4"} — Palavra Fácil: ativa o power-up para a próxima ronda</li>
   * </ul>
   *
   * @param acao   o atalho do power-up a usar ({@code "1"}, {@code "2"}, {@code "3"} ou {@code "4"})
   * @param palavra a {@link Palavra} em jogo
   * @param forca   a {@link Forca} com o estado atual de vidas
   * @param loja    a {@link Loja} de onde os power-ups são consumidos
   * @return o número de letras reveladas pelo power-up; {@code 0} se nenhuma letra foi revelada
   */
  private int usarItem(String acao, Palavra palavra, Forca forca, Loja loja) {
    if ("1".equals(acao)) {
      if (!loja.consumirDica()) {
        System.out.println(ConsolaUtils.erro("Não tens Dicas disponiveis."));
        return 0;
      }

      Character letra = palavra.getLetraOcultaAleatoria(random);
      if (letra == null) {
        System.out.println(ConsolaUtils.erro("Não há letras por revelar."));
        return 0;
      }

      int reveladas = palavra.revelarLetra(letra);
      System.out.println(ConsolaUtils.sucesso("A Dica revelou a letra " + letra + "."));
      return reveladas;
    }

    if ("2".equals(acao)) {
      if (!loja.consumirCongelarVida()) {
        System.out.println(ConsolaUtils.erro("Não tens Congelar Vida disponível."));
        return 0;
      }

      forca.ativarCongelarVida();
      System.out.println(ConsolaUtils.sucesso("Congelar Vida ativado para o próximo erro."));
      return 0;
    }

    if ("3".equals(acao)) {
      if (!loja.consumirRevelarVogais()) {
        System.out.println(ConsolaUtils.erro("Não tens Revelar Vogais disponivel."));
        return 0;
      }

      int reveladas = palavra.revelarVogais();
      if (reveladas == 0) {
        System.out.println(ConsolaUtils.erro("Não havia vogais por revelar."));
      } else {
        System.out.println(ConsolaUtils.sucesso("As vogais foram reveladas."));
      }
      return reveladas;
    }

    if (!loja.ativarPalavraFacil()) {
      if (loja.temPalavraFacilAtiva()) {
        System.out.println(ConsolaUtils.erro("Já tens Palavra Fácil ativa para a próxima ronda."));
      } else {
        System.out.println(ConsolaUtils.erro("Não tens Palavra Fácil disponivel."));
      }
      return 0;
    }

    System.out.println(ConsolaUtils.sucesso("Palavra Fácil ativada para a próxima ronda."));
    return 0;
  }

  /**
   * Formata a linha de atalho de um power-up para apresentação na consola.
   *
   * <p>Se a quantidade for zero, apresenta o item como indisponível através
   * de {@link ConsolaUtils#desativado(String)}. Caso contrário, apresenta-o
   * normalmente com a quantidade disponível.</p>
   *
   * @param atalho    o atalho de teclado associado ao item (ex: {@code "1"})
   * @param nome      o nome do item a apresentar
   * @param quantidade a quantidade do item disponível no inventário
   * @return a linha formatada para apresentação na consola
   */
  private String formatarAtalhoItem(String atalho, String nome, int quantidade) {
    if (quantidade <= 0) {
      return ConsolaUtils.desativado("[" + atalho + "] " + nome + " (tens: 0) - Indisponível");
    }

    return ConsolaUtils.menu("[" + atalho + "] " + nome + " (tens: " + quantidade + ")");
  }
}