package tp1.tests_ParteD;

import tp1.ParteD_library.Palavra;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários para a classe {@link Palavra}.
 *
 * <p>Valida a revelação de letras, o comportamento com letras erradas,
 * o tratamento de expressões com espaços e a revelação de vogais.</p>
 */
class PalavraTest {

  /**
   * Verifica que revelar uma letra correta atualiza todas as suas
   * ocorrências no progresso da palavra.
   */
  @Test
  void revelarLetraDeveRevelarTodasAsOcorrencias() {
    Palavra palavra = new Palavra("BANANA");
    assertEquals(3, palavra.revelarLetra('A'));
    assertEquals("_ A _ A _ A", palavra.getProgressoFormatado());
  }

  /**
   * Verifica que revelar uma letra inexistente na palavra não altera
   * o progresso e a palavra permanece não descoberta.
   */
  @Test
  void letraErradaNaoDeveAlterarOProgresso() {
    Palavra palavra = new Palavra("TIGRE");
    assertEquals(0, palavra.revelarLetra('Z'));
    assertEquals("_ _ _ _ _", palavra.getProgressoFormatado());
    assertFalse(palavra.estaDescoberta());
  }

  /**
   * Verifica que expressões compostas (com espaços) mantêm os espaços
   * visíveis no progresso, que o número de letras exclui os espaços,
   * e que a adivinhação da palavra completa é insensível a maiúsculas
   * mas exige a expressão completa.
   */
  @Test
  void expressoesDevemManterEspacosVisiveisEExigirATextoCompleto() {
    Palavra palavra = new Palavra("FUTEBOL AMERICANO");
    assertTrue(palavra.getProgressoFormatado().contains("  "));
    assertEquals(16, palavra.getNumeroDeLetras());
    assertTrue(palavra.acertouPalavraCompleta("futebol americano"));
    assertFalse(palavra.acertouPalavraCompleta("futebol"));
  }

  /**
   * Verifica que {@link Palavra#revelarVogais()} revela apenas as vogais
   * ainda ocultas, devolvendo o número correto de revelações.
   */
  @Test
  void revelarVogaisDeveRevelarApenasAsVogaisPendentes() {
    Palavra palavra = new Palavra("CASA");
    assertEquals(2, palavra.revelarVogais());
    assertEquals("_ A _ A", palavra.getProgressoFormatado());
  }
}