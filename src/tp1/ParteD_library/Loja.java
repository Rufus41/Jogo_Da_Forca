package tp1.ParteD_library;

import java.util.Locale;

/**
 * Representa a loja de power-ups do jogo da Forca.
 *
 * <p>Gere o saldo do jogador e as quantidades de cada item disponível para compra
 * e consumo durante as rondas. Os itens disponíveis são:</p>
 * <ul>
 *   <li>{@link #DICA} — revela uma letra oculta aleatória</li>
 *   <li>{@link #CONGELAR_VIDA} — impede a perda de uma vida numa tentativa errada</li>
 *   <li>{@link #REVELAR_VOGAIS} — revela todas as vogais ainda ocultas</li>
 *   <li>{@link #PALAVRA_FACIL} — garante uma palavra mais curta na próxima ronda</li>
 * </ul>
 */
public class Loja {

  /** Identificador do item Dica. */
  public static final int DICA = 1;

  /** Identificador do item Congelar Vida. */
  public static final int CONGELAR_VIDA = 2;

  /** Identificador do item Revelar Vogais. */
  public static final int REVELAR_VOGAIS = 3;

  /** Identificador do item Palavra Fácil. */
  public static final int PALAVRA_FACIL = 4;

  /** Custo em pontos do item Dica. */
  private static final int CUSTO_DICA = 50;

  /** Custo em pontos do item Congelar Vida. */
  private static final int CUSTO_CONGELAR_VIDA = 150;

  /** Custo em pontos do item Revelar Vogais. */
  private static final int CUSTO_REVELAR_VOGAIS = 200;

  /** Custo em pontos do item Palavra Fácil. */
  private static final int CUSTO_PALAVRA_FACIL = 250;

  /** Saldo atual do jogador em pontos. */
  private double saldo;

  /** Quantidade de Dicas disponíveis. */
  private int quantidadeDicas;

  /** Quantidade de Congelar Vida disponíveis. */
  private int quantidadeCongelarVida;

  /** Quantidade de Revelar Vogais disponíveis. */
  private int quantidadeRevelarVogais;

  /** Quantidade de Palavra Fácil disponíveis. */
  private int quantidadePalavraFacil;

  /** Indica se o power-up Palavra Fácil está ativo para a próxima ronda. */
  private boolean proximaPalavraFacil;

  /**
   * Adiciona pontos ao saldo do jogador.
   *
   * @param pontos o valor a adicionar ao saldo
   */
  public void adicionarPontos(double pontos) {
    saldo += pontos;
  }

  /**
   * Tenta comprar um item da loja.
   *
   * <p>Verifica se o saldo é suficiente para cobrir o custo do item.
   * Em caso afirmativo, desconta o custo e incrementa a quantidade do item.
   * Se o identificador do item for inválido, o saldo é reposto e devolve
   * {@code false}.</p>
   *
   * @param item o identificador do item a comprar ({@link #DICA},
   *             {@link #CONGELAR_VIDA}, {@link #REVELAR_VOGAIS} ou {@link #PALAVRA_FACIL})
   * @return {@code true} se a compra foi efetuada com sucesso;
   *         {@code false} se o saldo for insuficiente ou o item for inválido
   */
  public boolean comprarItem(int item) {
    int custo = getCustoItem(item);

    if (saldo < custo) {
      return false;
    }

    saldo -= custo;

    if (item == DICA) {
      quantidadeDicas++;
    } else if (item == CONGELAR_VIDA) {
      quantidadeCongelarVida++;
    } else if (item == REVELAR_VOGAIS) {
      quantidadeRevelarVogais++;
    } else if (item == PALAVRA_FACIL) {
      quantidadePalavraFacil++;
    } else {
      saldo += custo;
      return false;
    }

    return true;
  }

  /**
   * Consome uma Dica do inventário do jogador.
   *
   * @return {@code true} se a Dica foi consumida com sucesso;
   *         {@code false} se não existirem Dicas disponíveis
   */
  public boolean consumirDica() {
    if (quantidadeDicas <= 0) {
      return false;
    }

    quantidadeDicas--;
    return true;
  }

  /**
   * Consome um Congelar Vida do inventário do jogador.
   *
   * @return {@code true} se o Congelar Vida foi consumido com sucesso;
   *         {@code false} se não existirem Congelar Vida disponíveis
   */
  public boolean consumirCongelarVida() {
    if (quantidadeCongelarVida <= 0) {
      return false;
    }

    quantidadeCongelarVida--;
    return true;
  }

  /**
   * Consome um Revelar Vogais do inventário do jogador.
   *
   * @return {@code true} se o Revelar Vogais foi consumido com sucesso;
   *         {@code false} se não existirem Revelar Vogais disponíveis
   */
  public boolean consumirRevelarVogais() {
    if (quantidadeRevelarVogais <= 0) {
      return false;
    }

    quantidadeRevelarVogais--;
    return true;
  }

  /**
   * Ativa o power-up Palavra Fácil para a próxima ronda.
   *
   * <p>Só é possível ativar se existir pelo menos um Palavra Fácil no inventário
   * e se o power-up ainda não estiver ativo.</p>
   *
   * @return {@code true} se o power-up foi ativado com sucesso;
   *         {@code false} se não existirem unidades disponíveis ou já estiver ativo
   */
  public boolean ativarPalavraFacil() {
    if (quantidadePalavraFacil <= 0 || proximaPalavraFacil) {
      return false;
    }

    quantidadePalavraFacil--;
    proximaPalavraFacil = true;
    return true;
  }

  /**
   * Consome o estado ativo do power-up Palavra Fácil.
   *
   * <p>Devolve o estado atual e desativa o power-up, independentemente
   * de estar ou não ativo.</p>
   *
   * @return {@code true} se o power-up estava ativo; {@code false} caso contrário
   */
  public boolean consumirPalavraFacilAtiva() {
    boolean ativa = proximaPalavraFacil;
    proximaPalavraFacil = false;
    return ativa;
  }

  /**
   * Verifica se o power-up Palavra Fácil está ativo para a próxima ronda.
   *
   * @return {@code true} se o power-up estiver ativo; {@code false} caso contrário
   */
  public boolean temPalavraFacilAtiva() {
    return proximaPalavraFacil;
  }

  /**
   * Devolve o saldo atual do jogador.
   *
   * @return o saldo em pontos
   */
  public double getSaldo() {
    return saldo;
  }

  /**
   * Devolve a quantidade de Dicas disponíveis no inventário.
   *
   * @return o número de Dicas disponíveis
   */
  public int getQuantidadeDicas() {
    return quantidadeDicas;
  }

  /**
   * Devolve a quantidade de Congelar Vida disponíveis no inventário.
   *
   * @return o número de Congelar Vida disponíveis
   */
  public int getQuantidadeCongelarVida() {
    return quantidadeCongelarVida;
  }

  /**
   * Devolve a quantidade de Revelar Vogais disponíveis no inventário.
   *
   * @return o número de Revelar Vogais disponíveis
   */
  public int getQuantidadeRevelarVogais() {
    return quantidadeRevelarVogais;
  }

  /**
   * Devolve a quantidade de Palavra Fácil disponíveis no inventário.
   *
   * @return o número de Palavra Fácil disponíveis
   */
  public int getQuantidadePalavraFacil() {
    return quantidadePalavraFacil;
  }

  /**
   * Devolve o custo em pontos de um item da loja.
   *
   * <p>Se o identificador não corresponder a nenhum item válido,
   * devolve {@link Integer#MAX_VALUE} para impedir a compra.</p>
   *
   * @param item o identificador do item ({@link #DICA}, {@link #CONGELAR_VIDA},
   *             {@link #REVELAR_VOGAIS} ou {@link #PALAVRA_FACIL})
   * @return o custo do item em pontos, ou {@link Integer#MAX_VALUE} se o item for inválido
   */
  public int getCustoItem(int item) {
    if (item == DICA) {
      return CUSTO_DICA;
    }
    if (item == CONGELAR_VIDA) {
      return CUSTO_CONGELAR_VIDA;
    }
    if (item == REVELAR_VOGAIS) {
      return CUSTO_REVELAR_VOGAIS;
    }
    if (item == PALAVRA_FACIL) {
      return CUSTO_PALAVRA_FACIL;
    }
    return Integer.MAX_VALUE;
  }

  /**
   * Devolve o saldo formatado como string para apresentação ao utilizador.
   *
   * <p>Se o saldo for um número inteiro, devolve-o sem casas decimais.
   * Caso contrário, formata com uma casa decimal usando o ponto como separador
   * decimal (locale {@link Locale#US}).</p>
   *
   * @return o saldo formatado como {@code String}
   */
  public String formatarSaldo() {
    if (saldo == Math.floor(saldo)) {
      return String.valueOf((int) saldo);
    }

    return String.format(Locale.US, "%.1f", saldo);
  }
}