package tp1.ParteD_library;

import java.util.Locale;

/**
 * Representa o registo de uma ronda concluída do jogo da Forca.
 *
 * <p>Armazena os dados relevantes de uma ronda — palavra, categoria,
 * dificuldade, resultado, pontuação e número de turnos — para posterior
 * consulta no histórico e no recorde da sessão.</p>
 *
 * @see Jogo
 */
public class HistoricoRonda {

  /** A palavra que foi jogada nesta ronda. */
  private final String palavra;

  /** A categoria da palavra jogada. */
  private final String categoria;

  /** O nível de dificuldade escolhido para esta ronda. */
  private final String dificuldade;

  /** O resultado da ronda (ex: {@code "Vitoria"} ou {@code "Derrota"}). */
  private final String resultado;

  /** A pontuação obtida no final da ronda. */
  private final double pontuacao;

  /** O número de turnos decorridos durante a ronda. */
  private final int numeroTurnos;

  /**
   * Constrói um novo {@code HistoricoRonda} com os dados da ronda concluída.
   *
   * @param palavra      a palavra que foi jogada
   * @param categoria    a categoria da palavra
   * @param dificuldade  o nível de dificuldade da ronda
   * @param resultado    o resultado da ronda ({@code "Vitoria"} ou {@code "Derrota"})
   * @param pontuacao    a pontuação final obtida
   * @param numeroTurnos o número de turnos decorridos
   */
  public HistoricoRonda(String palavra, String categoria, String dificuldade,
      String resultado, double pontuacao, int numeroTurnos) {
    this.palavra = palavra;
    this.categoria = categoria;
    this.dificuldade = dificuldade;
    this.resultado = resultado;
    this.pontuacao = pontuacao;
    this.numeroTurnos = numeroTurnos;
  }

  /**
   * Devolve a pontuação obtida nesta ronda.
   *
   * @return a pontuação final da ronda
   */
  public double getPontuacao() {
    return pontuacao;
  }

  /**
   * Devolve uma representação textual da ronda para apresentação no histórico.
   *
   * <p>O formato produzido é:</p>
   * <pre>
   * Palavra: &lt;palavra&gt; | Categoria: &lt;categoria&gt; | Dificuldade: &lt;dificuldade&gt; | Resultado: &lt;resultado&gt; | Pontuação: &lt;pontuacao&gt; | Turnos: &lt;numeroTurnos&gt;
   * </pre>
   *
   * @return uma {@code String} com os dados da ronda formatados
   */
  @Override
  public String toString() {
    return "Palavra: " + palavra
        + " | Categoria: " + categoria
        + " | Dificuldade: " + dificuldade
        + " | Resultado: " + resultado
        + " | Pontuação: " + formatarPontuacao(pontuacao)
        + " | Turnos: " + numeroTurnos;
  }

  /**
   * Formata um valor de pontuação para apresentação ao utilizador.
   *
   * <p>Se a pontuação for um número inteiro, devolve-o sem casas decimais.
   * Caso contrário, formata com uma casa decimal usando o ponto como separador
   * decimal (locale {@link Locale#US}).</p>
   *
   * @param valor o valor de pontuação a formatar
   * @return a pontuação formatada como {@code String}
   */
  private String formatarPontuacao(double valor) {
    if (valor == Math.floor(valor)) {
      return String.valueOf((int) valor);
    }

    return String.format(Locale.US, "%.1f", valor);
  }
}