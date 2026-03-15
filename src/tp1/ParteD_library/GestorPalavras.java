package tp1.ParteD_library;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe responsável pela gestão das palavras e categorias do jogo da Forca.
 *
 * <p>Contém um dicionário estático de categorias e as respetivas palavras,
 * e disponibiliza métodos para sortear palavras e consultar as categorias
 * e palavras disponíveis.</p>
 *
 * <p>Categorias disponíveis: Animais, Países, Desportos, Tecnologia, Filmes, Comida.</p>
 *
 * @see Palavra
 */
public class GestorPalavras {

  /**
   * Mapa estático que associa cada categoria ao seu array de palavras.
   * A ordem de inserção é preservada através de {@link LinkedHashMap}.
   */
  private static final Map<String, String[]> CATEGORIAS = new LinkedHashMap<>();

  /** Gerador de números aleatórios utilizado para sortear palavras. */
  private final Random random = new Random();

  static {
    CATEGORIAS.put("Animais", new String[] {
      "ELEFANTE", "TIGRE", "COELHO", "GIRAFA", "CANGURU",
      "LEOPARDO", "GOLFINHO", "PANTERA", "TARTARUGA", "RINOCERONTE",
      "URSO PARDO", "LOBO MARINHO", "AGUIA REAL", "FOCA CINZENTA",
      "CAVALO MARINHO", "PANDA VERMELHO", "LINCE IBERICO", "ORCA",
      "CAMELO", "HIPOPOTAMO"
    });
    CATEGORIAS.put("Países", new String[] {
      "PORTUGAL", "BRASIL", "JAPAO", "CANADA", "ANGOLA",
      "MEXICO", "NORUEGA", "ARGENTINA", "SENEGAL", "AUSTRALIA",
      "COREIA DO SUL", "ARABIA SAUDITA", "NOVA ZELANDIA", "ESTADOS UNIDOS",
      "REPUBLICA CHECA", "AFRICA DO SUL", "REINO UNIDO", "CABO VERDE",
      "COSTA RICA", "PAISES BAIXOS"
    });
    CATEGORIAS.put("Desportos", new String[] {
      "FUTEBOL", "TENIS", "NATACAO", "ATLETISMO", "BASQUETEBOL",
      "ANDEBOL", "VOLEIBOL", "RAGUEBI", "GINASTICA", "CICLISMO",
      "FUTEBOL AMERICANO", "TENIS DE MESA", "PATINAGEM ARTISTICA",
      "SALTOS ORNAMENTAIS", "ARTES MARCIAIS", "POLO AQUATICO",
      "BTT", "SURF", "BADMINTON", "KARATE"
    });
    CATEGORIAS.put("Tecnologia", new String[] {
      "COMPUTADOR", "ALGORITMO", "INTERNET", "PROGRAMACAO", "BASEDEDADOS",
      "ROBOTICA", "PROCESSADOR", "APLICACAO", "SERVIDOR", "CRIPTOGRAFIA",
      "INTELIGENCIA ARTIFICIAL", "REALIDADE VIRTUAL", "PIXEL", "CODIGO",
      "APRENDIZAGEM AUTOMATICA", "VISAO COMPUTACIONAL", "REDE NEURONAL",
      "COMPUTACAO GRAFICA", "ARMAZENAMENTO CLOUD", "LINHA DE COMANDO"
    });
    CATEGORIAS.put("Filmes", new String[] {
      "GLADIADOR", "INTERSTELLAR", "TITANIC", "AVATAR", "INCEPTION",
      "O REI LEAO", "GUERRA DAS ESTRELAS", "PARQUE JURASSICO", "MAD MAX",
      "MATRIX", "SENHOR DOS ANEIS", "VELOZES E FURIOSOS",
      "A VIDA E BELA", "ILHA DO MEDO"
    });
    CATEGORIAS.put("Comida", new String[] {
      "PIZZA", "LASANHA", "SUSHI", "BACALHAU", "HAMBURGUER",
      "ARROZ DOCE", "PAO DE LO", "FRANGO ASSADO", "BATATAS FRITAS",
      "CALDO VERDE", "ESPETADA MADEIRENSE", "MOUSSE DE CHOCOLATE",
      "SALADA RUSSA", "FEIJOADA"
    });
  }

  /**
   * Sorteia aleatoriamente uma palavra da categoria indicada.
   *
   * <p>Se o power-up Palavra Fácil estiver ativo, o método tenta selecionar
   * uma palavra com 6 ou menos letras (excluindo espaços). Se não existirem
   * palavras fáceis na categoria, sorteia normalmente de entre todas as palavras.</p>
   *
   * @param categoria    o nome da categoria de onde sortear a palavra
   * @param palavraFacil {@code true} se o power-up Palavra Fácil está ativo;
   *                     {@code false} para sortear normalmente
   * @return uma nova {@link Palavra} construída a partir da palavra sorteada
   * @throws IllegalArgumentException se a categoria fornecida não existir
   */
  public Palavra sortearPalavra(String categoria, boolean palavraFacil) {
    String[] palavras = CATEGORIAS.get(categoria);

    if (palavras == null) {
      throw new IllegalArgumentException("Categoria inválida: " + categoria);
    }

    if (palavraFacil) {
      List<String> palavrasFaceis = new LinkedList<>();

      for (String palavra : palavras) {
        if (contarLetras(palavra) <= 6) {
          palavrasFaceis.add(palavra);
        }
      }

      if (!palavrasFaceis.isEmpty()) {
        int indiceFacil = random.nextInt(palavrasFaceis.size());
        return new Palavra(palavrasFaceis.get(indiceFacil));
      }
    }

    int indice = random.nextInt(palavras.length);
    return new Palavra(palavras[indice]);
  }

  /**
   * Devolve os nomes de todas as categorias disponíveis.
   *
   * <p>A ordem das categorias é a ordem de inserção no mapa.</p>
   *
   * @return um array {@code String[]} com os nomes das categorias disponíveis
   */
  public String[] getCategoriasDisponiveis() {
    return CATEGORIAS.keySet().toArray(new String[0]);
  }

  /**
   * Devolve uma cópia do array de palavras de uma categoria.
   *
   * <p>É devolvida uma cópia do array original para evitar modificações externas
   * ao dicionário interno.</p>
   *
   * @param categoria o nome da categoria a consultar
   * @return um array {@code String[]} com as palavras da categoria
   * @throws IllegalArgumentException se a categoria fornecida não existir
   */
  public String[] getPalavrasDaCategoria(String categoria) {
    String[] palavras = CATEGORIAS.get(categoria);

    if (palavras == null) {
      throw new IllegalArgumentException("Categoria inválida: " + categoria);
    }

    return palavras.clone();
  }

  /**
   * Conta o número de letras de uma palavra, excluindo espaços.
   *
   * <p>Utilizado para determinar se uma palavra é elegível para o
   * power-up Palavra Fácil (máximo de 6 letras).</p>
   *
   * @param palavra a palavra a analisar
   * @return o número de caracteres que não são espaços
   */
  private int contarLetras(String palavra) {
    int total = 0;

    for (int i = 0; i < palavra.length(); i++) {
      if (palavra.charAt(i) != ' ') {
        total++;
      }
    }

    return total;
  }
}