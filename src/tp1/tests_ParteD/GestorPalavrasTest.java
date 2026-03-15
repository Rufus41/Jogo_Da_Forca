package tp1.tests_ParteD;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tp1.ParteD_library.GestorPalavras;
import tp1.ParteD_library.Palavra;

/**
 * Testes unitários para a classe {@link GestorPalavras}.
 *
 * <p>Valida a presença das categorias principais, a existência de palavras
 * com espaços, o filtro de palavras fáceis e a imutabilidade do array
 * devolvido por {@link GestorPalavras#getPalavrasDaCategoria(String)}.</p>
 */
class GestorPalavrasTest {

  /**
   * Verifica que as categorias principais estão presentes no gestor de palavras.
   */
  @Test
  void categoriasPrincipaisDevemExistir() {
    GestorPalavras gestor = new GestorPalavras();
    String[] categorias = gestor.getCategoriasDisponiveis();
    assertTrue(Arrays.asList(categorias).contains("Animais"));
    assertTrue(Arrays.asList(categorias).contains("Paises"));
    assertTrue(Arrays.asList(categorias).contains("Desportos"));
    assertTrue(Arrays.asList(categorias).contains("Tecnologia"));
  }

  /**
   * Verifica que as categorias Desportos e Tecnologia contêm pelo menos
   * uma palavra com espaços (expressões compostas).
   */
  @Test
  void categoriasDevemConterExpressoesComEspacos() {
    GestorPalavras gestor = new GestorPalavras();
    assertTrue(Arrays.stream(gestor.getPalavrasDaCategoria("Desportos")).anyMatch(p -> p.contains(" ")));
    assertTrue(Arrays.stream(gestor.getPalavrasDaCategoria("Tecnologia")).anyMatch(p -> p.contains(" ")));
  }

  /**
   * Verifica que o filtro de Palavra Fácil devolve exclusivamente palavras
   * com 6 ou menos letras, testando 20 sorteios consecutivos na categoria Comida.
   */
  @Test
  void filtroDePalavraFacilDeveSortearApenasPalavrasComSeisLetrasOuMenos() {
    GestorPalavras gestor = new GestorPalavras();
    for (int i = 0; i < 20; i++) {
      Palavra palavra = gestor.sortearPalavra("Comida", true);
      assertTrue(palavra.getNumeroDeLetras() <= 6);
    }
  }

  /**
   * Verifica que {@link GestorPalavras#getPalavrasDaCategoria(String)} devolve
   * uma cópia do array interno, garantindo que alterações externas não afetam
   * o dicionário original.
   */
  @Test
  void getPalavrasDaCategoriaDeveDevolverUmaCopiaDoArray() {
    GestorPalavras gestor = new GestorPalavras();
    String[] original = gestor.getPalavrasDaCategoria("Animais");
    String[] copia = gestor.getPalavrasDaCategoria("Animais");
    assertArrayEquals(original, copia);
    copia[0] = "ALTERADA";
    assertNotEquals("ALTERADA", gestor.getPalavrasDaCategoria("Animais")[0]);
  }
}