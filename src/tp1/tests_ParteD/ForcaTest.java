package tp1.tests_ParteD;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tp1.ParteD_library.Forca;

/**
 * Testes unitários para a classe {@link Forca}.
 *
 * <p>Valida o comportamento do registo de erros, a lógica de derrota
 * e o funcionamento do power-up Congelar Vida.</p>
 */
class ForcaTest {

  /**
   * Verifica que o registo de erros incrementa o contador corretamente
   * e que a forca reconhece a derrota ao atingir o limite de vidas.
   */
  @Test
  void registarErroDeveIncrementarAtePerder() {
    Forca forca = new Forca(4);
    assertTrue(forca.registarErro());
    assertTrue(forca.registarErro());
    assertTrue(forca.registarErro());
    assertTrue(forca.registarErro());
    assertEquals(4, forca.getErros());
    assertTrue(forca.perdeu());
  }

  /**
   * Verifica que o power-up Congelar Vida absorve apenas o primeiro erro
   * após ser ativado, sendo consumido de seguida, e que os erros subsequentes
   * são registados normalmente.
   */
  @Test
  void congelarVidaDeveProtegerApenasOProximoErro() {
    Forca forca = new Forca(4);
    forca.ativarCongelarVida();
    assertFalse(forca.registarErro());
    assertEquals(0, forca.getErros());
    assertFalse(forca.temVidaCongelada());
    assertTrue(forca.registarErro());
    assertEquals(1, forca.getErros());
  }
}