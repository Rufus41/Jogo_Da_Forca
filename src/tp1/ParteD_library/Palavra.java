package tp1.ParteD_library;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Representa uma palavra no jogo da Forca, gerindo o seu progresso de descoberta.
 *
 * <p>Armazena o texto original da palavra e um array de progresso onde cada
 * posição contém a letra revelada, um espaço (se for um espaço na palavra original)
 * ou {@code '_'} se ainda não foi descoberta.</p>
 */
public class Palavra {

  /** Texto original da palavra, convertido para maiúsculas. */
  private final String textoOriginal;

  /**
   * Array de progresso da palavra.
   * Cada posição contém a letra revelada, {@code ' '} se for um espaço,
   * ou {@code '_'} se a letra ainda não foi descoberta.
   */
  private final char[] progresso;

  /**
   * Constrói uma nova {@code Palavra} a partir do texto fornecido.
   *
   * <p>O texto é convertido para maiúsculas e o array de progresso é
   * inicializado com {@code '_'} para cada letra e {@code ' '} para
   * cada espaço.</p>
   *
   * @param textoOriginal o texto da palavra a adivinhar
   */
  public Palavra(String textoOriginal) {
    this.textoOriginal = textoOriginal.toUpperCase();
    this.progresso = new char[this.textoOriginal.length()];

    for (int i = 0; i < this.textoOriginal.length(); i++) {
      char caractere = this.textoOriginal.charAt(i);
      progresso[i] = isEspaco(caractere) ? ' ' : '_';
    }
  }

  /**
   * Revela todas as ocorrências de uma letra na palavra.
   *
   * <p>Percorre o texto original e, para cada posição onde a letra coincide
   * e ainda não foi revelada ({@code '_'}), atualiza o progresso e incrementa
   * o contador de revelações.</p>
   *
   * @param letra a letra a revelar (em maiúscula)
   * @return o número de posições reveladas; {@code 0} se a letra não existir
   *         ou já tiver sido revelada anteriormente
   */
  public int revelarLetra(char letra) {
    int revelacoes = 0;

    for (int i = 0; i < textoOriginal.length(); i++) {
      if (textoOriginal.charAt(i) == letra && progresso[i] == '_') {
        progresso[i] = letra;
        revelacoes++;
      }
    }

    return revelacoes;
  }

  /**
   * Verifica se uma tentativa de palavra completa está correta.
   *
   * <p>A comparação é feita ignorando espaços iniciais e finais e
   * convertendo a tentativa para maiúsculas antes de comparar com
   * o texto original.</p>
   *
   * @param tentativa a palavra introduzida pelo utilizador
   * @return {@code true} se a tentativa corresponder ao texto original;
   *         {@code false} caso contrário
   */
  public boolean acertouPalavraCompleta(String tentativa) {
    return textoOriginal.equals(tentativa.trim().toUpperCase());
  }

  /**
   * Revela todas as letras da palavra, independentemente do progresso atual.
   */
  public void revelarTudo() {
    for (int i = 0; i < textoOriginal.length(); i++) {
      progresso[i] = textoOriginal.charAt(i);
    }
  }

  /**
   * Devolve o número total de letras da palavra, excluindo espaços.
   *
   * @return o número de letras (não espaços) na palavra original
   */
  public int getNumeroDeLetras() {
    int total = 0;

    for (int i = 0; i < textoOriginal.length(); i++) {
      if (!isEspaco(textoOriginal.charAt(i))) {
        total++;
      }
    }

    return total;
  }

  /**
   * Verifica se a palavra foi completamente descoberta.
   *
   * @return {@code true} se não existir nenhuma posição com {@code '_'};
   *         {@code false} caso contrário
   */
  public boolean estaDescoberta() {
    for (char caractere : progresso) {
      if (caractere == '_') {
        return false;
      }
    }

    return true;
  }

  /**
   * Devolve o progresso atual da palavra formatado para apresentação.
   *
   * <p>Cada caractere do array de progresso é separado por um espaço,
   * sendo os espaços finais removidos antes de devolver a string.</p>
   *
   * @return uma {@code String} com os caracteres do progresso separados por espaços
   */
  public String getProgressoFormatado() {
    StringBuilder sb = new StringBuilder();

    for (char caractere : progresso) {
      sb.append(caractere).append(' ');
    }

    return sb.toString().trim();
  }

  /**
   * Devolve o texto original da palavra.
   *
   * @return o texto original em maiúsculas
   */
  public String getTextoOriginal() {
    return textoOriginal;
  }

  /**
   * Devolve uma letra oculta aleatória da palavra.
   *
   * <p>Recolhe todas as letras ainda não reveladas (posições com {@code '_'}
   * e que não sejam espaços), sem repetições, e devolve uma aleatória.
   * Devolve {@code null} se não existirem letras ocultas.</p>
   *
   * @param random o {@link Random} utilizado para a seleção aleatória
   * @return uma {@link Character} com uma letra oculta aleatória,
   *         ou {@code null} se a palavra já estiver completamente revelada
   */
  public Character getLetraOcultaAleatoria(Random random) {
    List<Character> letrasOcultas = new LinkedList<>();

    for (int i = 0; i < textoOriginal.length(); i++) {
      if (progresso[i] == '_' && !isEspaco(textoOriginal.charAt(i))) {
        char letra = textoOriginal.charAt(i);
        if (!letrasOcultas.contains(letra)) {
          letrasOcultas.add(letra);
        }
      }
    }

    if (letrasOcultas.isEmpty()) {
      return null;
    }

    return letrasOcultas.get(random.nextInt(letrasOcultas.size()));
  }

  /**
   * Revela todas as vogais ainda ocultas na palavra.
   *
   * <p>Invoca {@link #revelarLetra(char)} para cada vogal ({@code A, E, I, O, U})
   * e acumula o total de revelações.</p>
   *
   * @return o número total de vogais reveladas
   */
  public int revelarVogais() {
    int revelacoes = 0;

    for (char vogal : new char[] { 'A', 'E', 'I', 'O', 'U' }) {
      revelacoes += revelarLetra(vogal);
    }

    return revelacoes;
  }

  /**
   * Verifica se um caractere é um espaço.
   *
   * @param caractere o caractere a verificar
   * @return {@code true} se o caractere for {@code ' '}; {@code false} caso contrário
   */
  private boolean isEspaco(char caractere) {
    return caractere == ' ';
  }
}