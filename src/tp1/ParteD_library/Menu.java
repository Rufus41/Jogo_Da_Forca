package tp1.ParteD_library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pelo menu principal e pela navegação do jogo da Forca.
 *
 * <p>Gere o fluxo principal da aplicação, incluindo o menu principal, o início
 * de novas rondas, a loja de itens, o histórico de rondas e o recorde da sessão.</p>
 *
 * @see Jogo
 * @see Loja
 * @see GestorPalavras
 * @see HistoricoRonda
 */
public class Menu {

  /** Nomes das dificuldades disponíveis. */
  private static final String[] DIFICULDADES = { "Fácil", "Médio", "Difícil" };

  /** Número de vidas associado a cada dificuldade. */
  private static final int[] VIDAS = { 8, 6, 4 };

  /** Valor base de pontuação associado a cada dificuldade. */
  private static final int[] VALORES_BASE = { 10, 15, 20 };

  /**
   * Inicia o ciclo principal do programa.
   *
   * <p>Apresenta o menu principal em loop até o utilizador escolher sair (opção 5).
   * Em cada iteração, lê a opção escolhida e delega para o método correspondente:</p>
   * <ol>
   *   <li>Jogar em modo contínuo</li>
   *   <li>Abrir loja</li>
   *   <li>Ver histórico da sessão</li>
   *   <li>Ver recorde da sessão</li>
   *   <li>Sair</li>
   * </ol>
   */
  public void iniciar() {
    Scanner scanner = new Scanner(System.in);
    GestorPalavras gestorPalavras = new GestorPalavras();
    Jogo jogo = new Jogo(gestorPalavras, scanner);
    Loja loja = new Loja();
    List<HistoricoRonda> historico = new ArrayList<>();
    boolean sair = false;

    while (!sair) {
      ConsolaUtils.limparEcra();
      mostrarMenuPrincipal(loja);
      int opcao = lerOpcao(scanner, 5);

      if (opcao == 1) {
        jogarModoContinuo(scanner, gestorPalavras, jogo, historico, loja);
      } else if (opcao == 2) {
        abrirLoja(scanner, loja);
      } else if (opcao == 3) {
        ConsolaUtils.limparEcra();
        mostrarHistorico(historico);
        esperarVoltarAoMenu(scanner);
      } else if (opcao == 4) {
        ConsolaUtils.limparEcra();
        mostrarRecorde(historico);
        esperarVoltarAoMenu(scanner);
      } else {
        sair = true;
        ConsolaUtils.limparEcra();
        System.out.println(ConsolaUtils.sucesso("Programa terminado."));
      }

      System.out.println();
    }
  }

  /**
   * Apresenta o menu principal na consola.
   *
   * <p>Mostra o título do jogo, o saldo atual do jogador, uma indicação caso
   * o power-up Palavra Fácil esteja ativo, e as opções disponíveis.</p>
   *
   * @param loja a {@link Loja} de onde é obtido o saldo e o estado dos power-ups
   */
  private void mostrarMenuPrincipal(Loja loja) {
    System.out.println(ConsolaUtils.titulo("JOGO DA FORCA"));
    System.out.println(ConsolaUtils.neutro("Saldo atual: " + loja.formatarSaldo()));
    if (loja.temPalavraFacilAtiva()) {
      System.out.println(ConsolaUtils.sucesso("Palavra Fácil ativa para a próxima ronda."));
    }
    System.out.println(ConsolaUtils.menu("1 - Jogar"));
    System.out.println(ConsolaUtils.menu("2 - Loja"));
    System.out.println(ConsolaUtils.menu("3 - Ver Histórico"));
    System.out.println(ConsolaUtils.menu("4 - Ver Recorde"));
    System.out.println(ConsolaUtils.menu("5 - Sair"));
  }

  /**
   * Gere o fluxo de jogo em modo contínuo.
   *
   * <p>Corre em loop, solicitando ao utilizador a categoria e a dificuldade,
   * iniciando cada ronda através de {@link Jogo#jogar}, adicionando o resultado
   * ao histórico e atualizando o saldo da loja com a pontuação obtida.
   * No final de cada ronda, pergunta ao utilizador se deseja jogar outra através
   * de {@link #perguntarSeQuerOutraRonda(Scanner)}. O ciclo termina quando o
   * utilizador optar por voltar ao menu.</p>
   *
   * @param scanner        o {@link Scanner} utilizado para leitura da entrada do utilizador
   * @param gestorPalavras o {@link GestorPalavras} utilizado para obter as categorias disponíveis
   * @param jogo           o {@link Jogo} responsável pela lógica da ronda
   * @param historico      a lista de {@link HistoricoRonda} da sessão atual
   * @param loja           a {@link Loja} onde os pontos ganhos são adicionados
   */
  private void jogarModoContinuo(Scanner scanner, GestorPalavras gestorPalavras, Jogo jogo,
      List<HistoricoRonda> historico, Loja loja) {
    boolean continuarAJogar = true;

    while (continuarAJogar) {
      String categoria = escolherCategoria(scanner, gestorPalavras);
      int indiceDificuldade = escolherDificuldade(scanner);

      ConsolaUtils.limparEcra();
      System.out.println(ConsolaUtils.sucesso("Categoria escolhida: " + categoria));
      System.out.println(ConsolaUtils.sucesso("Dificuldade escolhida: " + DIFICULDADES[indiceDificuldade]
          + " (" + VIDAS[indiceDificuldade] + " vidas)"));
      System.out.println();

      HistoricoRonda ronda = jogo.jogar(
          categoria,
          DIFICULDADES[indiceDificuldade],
          VIDAS[indiceDificuldade],
          VALORES_BASE[indiceDificuldade],
          loja);

      historico.add(ronda);
      loja.adicionarPontos(ronda.getPontuacao());

      continuarAJogar = perguntarSeQuerOutraRonda(scanner);
    }
  }

  /**
   * Pergunta ao utilizador se deseja jogar outra ronda ou voltar ao menu.
   *
   * <p>Apresenta duas opções: jogar outra ronda (1) ou voltar ao menu (2).</p>
   *
   * @param scanner o {@link Scanner} utilizado para leitura da entrada do utilizador
   * @return {@code true} se o utilizador escolher jogar outra ronda;
   *         {@code false} se optar por voltar ao menu
   */
  private boolean perguntarSeQuerOutraRonda(Scanner scanner) {
    System.out.println();
    System.out.println(ConsolaUtils.menu("1 - Jogar outra ronda"));
    System.out.println(ConsolaUtils.menu("2 - Voltar ao menu"));

    return lerOpcao(scanner, 2) == 1;
  }

  /**
   * Gere o menu da loja de power-ups.
   *
   * <p>Apresenta os itens disponíveis e os seus custos em loop até o utilizador
   * escolher voltar (opção 5). Tenta efetuar a compra do item selecionado através
   * de {@link Loja#comprarItem(int)}, informando o utilizador do resultado.</p>
   *
   * @param scanner o {@link Scanner} utilizado para leitura da entrada do utilizador
   * @param loja    a {@link Loja} onde os itens são comprados
   */
  private void abrirLoja(Scanner scanner, Loja loja) {
    boolean voltar = false;

    while (!voltar) {
      ConsolaUtils.limparEcra();
      mostrarMenuLoja(loja);
      int opcao = lerOpcao(scanner, 5);

      if (opcao == 5) {
        voltar = true;
      } else if (loja.comprarItem(opcao)) {
        System.out.println(ConsolaUtils.sucesso("Compra efetuada com sucesso."));
      } else {
        System.out.println(ConsolaUtils.erro("Não tens pontos suficientes para essa compra."));
      }

      System.out.println();
      if (!voltar) {
        esperarContinuar(scanner);
      }
    }
  }

  /**
   * Apresenta o menu da loja na consola.
   *
   * <p>Mostra o saldo atual e os quatro itens disponíveis para compra,
   * com os respetivos custos e quantidades que o jogador já possui.</p>
   *
   * @param loja a {@link Loja} de onde são obtidos os preços e quantidades dos itens
   */
  private void mostrarMenuLoja(Loja loja) {
    System.out.println(ConsolaUtils.titulo("LOJA"));
    System.out.println(ConsolaUtils.neutro("Saldo atual: " + loja.formatarSaldo()));
    System.out.println(ConsolaUtils.menu("1 - Dica (" + loja.getCustoItem(Loja.DICA)
        + " pts) | tens: " + loja.getQuantidadeDicas()));
    System.out.println(ConsolaUtils.menu("2 - Congelar Vida (" + loja.getCustoItem(Loja.CONGELAR_VIDA)
        + " pts) | tens: " + loja.getQuantidadeCongelarVida()));
    System.out.println(ConsolaUtils.menu("3 - Revelar Vogais (" + loja.getCustoItem(Loja.REVELAR_VOGAIS)
        + " pts) | tens: " + loja.getQuantidadeRevelarVogais()));
    System.out.println(ConsolaUtils.menu("4 - Palavra Fácil (" + loja.getCustoItem(Loja.PALAVRA_FACIL)
        + " pts) | tens: " + loja.getQuantidadePalavraFacil()));
    System.out.println(ConsolaUtils.menu("5 - Voltar"));
  }

  /**
   * Apresenta o histórico de rondas jogadas na sessão atual.
   *
   * <p>Lista todas as rondas numeradas. Se ainda não existirem rondas jogadas,
   * informa o utilizador.</p>
   *
   * @param historico a lista de {@link HistoricoRonda} da sessão atual
   */
  private void mostrarHistorico(List<HistoricoRonda> historico) {
    System.out.println(ConsolaUtils.titulo("HISTÓRICO DA SESSÃO"));

    if (historico.isEmpty()) {
      System.out.println(ConsolaUtils.neutro("Ainda não existem rondas jogadas."));
      return;
    }

    for (int i = 0; i < historico.size(); i++) {
      System.out.println(ConsolaUtils.neutro((i + 1) + " - " + historico.get(i).toString()));
    }
  }

  /**
   * Apresenta a ronda com maior pontuação da sessão atual.
   *
   * <p>Percorre o histórico em busca da {@link HistoricoRonda} com a pontuação
   * mais alta e apresenta-a na consola. Se ainda não existirem rondas jogadas,
   * informa o utilizador.</p>
   *
   * @param historico a lista de {@link HistoricoRonda} da sessão atual
   */
  private void mostrarRecorde(List<HistoricoRonda> historico) {
    System.out.println(ConsolaUtils.titulo("RECORDE DA SESSÃO"));

    if (historico.isEmpty()) {
      System.out.println(ConsolaUtils.neutro("Ainda não existem rondas jogadas."));
      return;
    }

    HistoricoRonda melhorRonda = historico.get(0);

    for (HistoricoRonda ronda : historico) {
      if (ronda.getPontuacao() > melhorRonda.getPontuacao()) {
        melhorRonda = ronda;
      }
    }

    System.out.println(ConsolaUtils.sucesso(melhorRonda.toString()));
  }

  /**
   * Solicita ao utilizador que escolha uma categoria de palavras.
   *
   * <p>Apresenta as categorias disponíveis obtidas através de
   * {@link GestorPalavras#getCategoriasDisponiveis()} e devolve
   * a categoria selecionada.</p>
   *
   * @param scanner        o {@link Scanner} utilizado para leitura da entrada do utilizador
   * @param gestorPalavras o {@link GestorPalavras} de onde são obtidas as categorias
   * @return a categoria escolhida pelo utilizador
   */
  private String escolherCategoria(Scanner scanner, GestorPalavras gestorPalavras) {
    String[] categorias = gestorPalavras.getCategoriasDisponiveis();

    ConsolaUtils.limparEcra();
    System.out.println(ConsolaUtils.titulo("ESCOLHER CATEGORIA"));
    for (int i = 0; i < categorias.length; i++) {
      System.out.println(ConsolaUtils.menu((i + 1) + " - " + categorias[i]));
    }

    int opcao = lerOpcao(scanner, categorias.length);
    return categorias[opcao - 1];
  }

  /**
   * Solicita ao utilizador que escolha um nível de dificuldade.
   *
   * <p>Apresenta as dificuldades disponíveis com o número de vidas
   * correspondente e devolve o índice da dificuldade selecionada.</p>
   *
   * @param scanner o {@link Scanner} utilizado para leitura da entrada do utilizador
   * @return o índice (base 0) da dificuldade escolhida em {@code DIFICULDADES}
   */
  private int escolherDificuldade(Scanner scanner) {
    ConsolaUtils.limparEcra();
    System.out.println(ConsolaUtils.titulo("ESCOLHER DIFICULDADE"));
    for (int i = 0; i < DIFICULDADES.length; i++) {
      System.out.println(ConsolaUtils.menu((i + 1) + " - " + DIFICULDADES[i] + " (" + VIDAS[i] + " vidas)"));
    }

    return lerOpcao(scanner, DIFICULDADES.length) - 1;
  }

  /**
   * Lê e valida uma opção numérica introduzida pelo utilizador.
   *
   * <p>Repete o pedido enquanto a entrada não for um número inteiro válido
   * no intervalo {@code [1, maximo]}. Entradas não numéricas são tratadas
   * e o pedido é repetido.</p>
   *
   * @param scanner o {@link Scanner} utilizado para leitura da entrada do utilizador
   * @param maximo  o valor máximo aceite (inclusive)
   * @return o inteiro introduzido, garantidamente dentro de {@code [1, maximo]}
   */
  private int lerOpcao(Scanner scanner, int maximo) {
    while (true) {
      System.out.print(ConsolaUtils.menu("Opção: "));
      String input = scanner.nextLine().trim();

      try {
        int opcao = Integer.parseInt(input);
        if (opcao >= 1 && opcao <= maximo) {
          return opcao;
        }
      } catch (NumberFormatException e) {
        // Mantem o fluxo de validacao no aviso abaixo.
      }

      System.out.println(ConsolaUtils.erro("Escolhe uma opção valida."));
    }
  }

  /**
   * Aguarda que o utilizador prima Enter para voltar ao menu.
   *
   * @param scanner o {@link Scanner} utilizado para leitura da entrada do utilizador
   */
  private void esperarVoltarAoMenu(Scanner scanner) {
    System.out.print(ConsolaUtils.menu("Prime Enter para voltar ao menu..."));
    scanner.nextLine();
  }

  /**
   * Aguarda que o utilizador prima Enter para continuar.
   *
   * @param scanner o {@link Scanner} utilizado para leitura da entrada do utilizador
   */
  private void esperarContinuar(Scanner scanner) {
    System.out.print(ConsolaUtils.menu("Prime Enter para continuar..."));
    scanner.nextLine();
  }
}