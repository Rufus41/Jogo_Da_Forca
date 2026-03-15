package tp1.ParteD_library;

import java.io.IOException;

/**
 * Utilitários de formatação e interação com a consola para o jogo da Forca.
 *
 * <p>Classe utilitária não instanciável que fornece métodos estáticos para
 * limpar o ecrã e formatar texto com cores ANSI para apresentação na consola.</p>
 *
 * <p>Cores utilizadas:</p>
 * <ul>
 *   <li>Ciano — títulos e opções de menu</li>
 *   <li>Verde — mensagens de sucesso</li>
 *   <li>Vermelho — mensagens de erro</li>
 *   <li>Amarelo — informação neutra</li>
 *   <li>Cinzento — itens desativados ou indisponíveis</li>
 * </ul>
 */
public final class ConsolaUtils {

  /** Código ANSI para repor a formatação predefinida. */
  private static final String RESET = "\u001B[0m";

  /** Código ANSI para texto verde. */
  private static final String VERDE = "\u001B[32m";

  /** Código ANSI para texto vermelho. */
  private static final String VERMELHO = "\u001B[31m";

  /** Código ANSI para texto amarelo. */
  private static final String AMARELO = "\u001B[33m";

  /** Código ANSI para texto ciano. */
  private static final String CIANO = "\u001B[36m";

  /** Código ANSI para texto cinzento escuro. */
  private static final String CINZENTO = "\u001B[90m";

  /**
   * Construtor privado para impedir a instanciação desta classe utilitária.
   */
  private ConsolaUtils() {
  }

  /**
   * Limpa o ecrã da consola de forma compatível com Windows e Unix.
   *
   * <p>Em Windows, executa o comando {@code cls} via {@link ProcessBuilder}.
   * Em outros sistemas, executa o comando {@code clear}.
   * Em caso de erro ({@link IOException} ou {@link InterruptedException}),
   * utiliza a sequência de escape ANSI {@code \033[H\033[2J} como alternativa.
   * Se a exceção for uma {@link InterruptedException}, o estado de interrupção
   * da thread é reposto com {@link Thread#interrupt()}.</p>
   */
  public static void limparEcra() {
    try {
      if (isWindows()) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        new ProcessBuilder("clear").inheritIO().start().waitFor();
      }
    } catch (IOException | InterruptedException e) {
      System.out.print("\033[H\033[2J");
      System.out.flush();
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Formata um texto com a cor de título (ciano).
   *
   * @param texto o texto a formatar
   * @return o texto envolvido nos códigos ANSI de cor ciano
   */
  public static String titulo(String texto) {
    return CIANO + texto + RESET;
  }

  /**
   * Formata um texto com a cor de menu (ciano).
   *
   * @param texto o texto a formatar
   * @return o texto envolvido nos códigos ANSI de cor ciano
   */
  public static String menu(String texto) {
    return CIANO + texto + RESET;
  }

  /**
   * Formata um texto com a cor de sucesso (verde).
   *
   * @param texto o texto a formatar
   * @return o texto envolvido nos códigos ANSI de cor verde
   */
  public static String sucesso(String texto) {
    return VERDE + texto + RESET;
  }

  /**
   * Formata um texto com a cor de erro (vermelho).
   *
   * @param texto o texto a formatar
   * @return o texto envolvido nos códigos ANSI de cor vermelha
   */
  public static String erro(String texto) {
    return VERMELHO + texto + RESET;
  }

  /**
   * Formata um texto com a cor neutra (amarelo).
   *
   * @param texto o texto a formatar
   * @return o texto envolvido nos códigos ANSI de cor amarela
   */
  public static String neutro(String texto) {
    return AMARELO + texto + RESET;
  }

  /**
   * Formata um texto com a cor de item desativado (cinzento).
   *
   * @param texto o texto a formatar
   * @return o texto envolvido nos códigos ANSI de cor cinzenta
   */
  public static String desativado(String texto) {
    return CINZENTO + texto + RESET;
  }

  /**
   * Verifica se o sistema operativo atual é Windows.
   *
   * @return {@code true} se o sistema operativo for Windows; {@code false} caso contrário
   */
  private static boolean isWindows() {
    return System.getProperty("os.name").toLowerCase().contains("win");
  }
}