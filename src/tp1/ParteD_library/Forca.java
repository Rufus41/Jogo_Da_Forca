package tp1.ParteD_library;

/**
 * Representa o estado da forca numa ronda do jogo da Forca.
 *
 * <p>Gere o número de erros, as vidas restantes, o estado da vida congelada
 * e o desenho ASCII da forca correspondente ao progresso da ronda.</p>
 *
 * @see Jogo
 */
public class Forca {

  /**
   * Array de desenhos ASCII que representam os estados progressivos da forca,
   * desde o estado inicial (sem erros) até ao estado final (figura completa).
   */
  private static final String[] ESTADOS = {
    """
     +---+
     |   |
         |
         |
         |
         |
    =========
    """,
    """
     +---+
     |   |
     O   |
         |
         |
         |
    =========
    """,
    """
     +---+
     |   |
     O   |
     |   |
         |
         |
    =========
    """,
    """
     +---+
     |   |
     O   |
    /|   |
         |
         |
    =========
    """,
    """
     +---+
     |   |
     O   |
    /|\\  |
         |
         |
    =========
    """,
    """
     +---+
     |   |
     O   |
    /|\\  |
    /    |
         |
    =========
    """,
    """
     +---+
     |   |
     O   |
    /|\\  |
    / \\  |
         |
    =========
    """
  };

  /** Número máximo de erros permitidos antes de perder. */
  private final int vidasMaximas;

  /** Número de erros cometidos até ao momento. */
  private int erros;

  /** Indica se o power-up Congelar Vida está ativo para o próximo erro. */
  private boolean vidaCongelada;

  /**
   * Constrói uma nova {@code Forca} com o número máximo de vidas definido.
   *
   * @param vidasMaximas o número máximo de erros permitidos antes de perder
   */
  public Forca(int vidasMaximas) {
    this.vidasMaximas = vidasMaximas;
  }

  /**
   * Regista um erro do utilizador.
   *
   * <p>Se o power-up Congelar Vida estiver ativo, o erro é absorvido — a vida
   * congelada é consumida e o contador de erros não é incrementado.
   * Caso contrário, o contador de erros é incrementado até ao máximo permitido.</p>
   *
   * @return {@code false} se a vida congelada absorveu o erro;
   *         {@code true} se o erro foi registado normalmente
   */
  public boolean registarErro() {
    if (vidaCongelada) {
      vidaCongelada = false;
      return false;
    }

    if (erros < vidasMaximas) {
      erros++;
    }

    return true;
  }

  /**
   * Verifica se o jogador perdeu a ronda.
   *
   * @return {@code true} se o número de erros atingiu ou ultrapassou o máximo;
   *         {@code false} caso contrário
   */
  public boolean perdeu() {
    return erros >= vidasMaximas;
  }

  /**
   * Devolve o número de erros cometidos até ao momento.
   *
   * @return o número de erros atuais
   */
  public int getErros() {
    return erros;
  }

  /**
   * Devolve o número de tentativas restantes.
   *
   * @return a diferença entre o máximo de vidas e o número de erros atuais
   */
  public int getTentativasRestantes() {
    return vidasMaximas - erros;
  }

  /**
   * Devolve o desenho ASCII da forca correspondente ao estado atual.
   *
   * <p>O índice do estado é calculado proporcionalmente ao número de erros
   * em relação ao total de vidas, usando arredondamento, de forma a distribuir
   * os 7 estados de desenho independentemente do número de vidas configurado.</p>
   *
   * @return uma {@code String} com o desenho ASCII da forca no estado atual
   */
  public String getDesenhoAtual() {
    int ultimoIndice = ESTADOS.length - 1;
    int indiceEstado = (int) Math.round((double) erros * ultimoIndice / vidasMaximas);

    return ESTADOS[Math.min(indiceEstado, ultimoIndice)];
  }

  /**
   * Ativa o power-up Congelar Vida.
   *
   * <p>Quando ativo, o próximo erro do utilizador será absorvido sem penalização,
   * sendo o poder consumido de seguida.</p>
   */
  public void ativarCongelarVida() {
    vidaCongelada = true;
  }

  /**
   * Verifica se o power-up Congelar Vida está ativo.
   *
   * @return {@code true} se a vida congelada está ativa; {@code false} caso contrário
   */
  public boolean temVidaCongelada() {
    return vidaCongelada;
  }
}