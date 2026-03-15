package tp1.tests_ParteD;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tp1.ParteD_library.Loja;

/**
 * Testes unitários para a classe {@link Loja}.
 *
 * <p>Valida a compra de itens, o desconto de saldo, o inventário, as compras
 * com saldo insuficiente, a ativação e consumo do power-up Palavra Fácil,
 * e o consumo de itens sem stock.</p>
 */
class LojaTest {

  /**
   * Verifica que a compra de um item desconta o custo do saldo e
   * incrementa a quantidade do item no inventário.
   */
  @Test
  void comprarItemDeveDescontarSaldoEAumentarInventario() {
    Loja loja = new Loja();
    loja.adicionarPontos(100);
    assertTrue(loja.comprarItem(Loja.DICA));
    assertEquals(50.0, loja.getSaldo(), 0.001);
    assertEquals(1, loja.getQuantidadeDicas());
  }

  /**
   * Verifica que a tentativa de compra com saldo insuficiente falha
   * e o saldo permanece inalterado.
   */
  @Test
  void comprarItemSemSaldoSuficienteDeveFalhar() {
    Loja loja = new Loja();
    loja.adicionarPontos(100);
    assertFalse(loja.comprarItem(Loja.REVELAR_VOGAIS));
    assertEquals(100.0, loja.getSaldo(), 0.001);
  }

  /**
   * Verifica o ciclo completo do power-up Palavra Fácil: compra, ativação,
   * consumo e verificação do estado após consumo.
   */
  @Test
  void ativarPalavraFacilDeveConsumirOItem() {
    Loja loja = new Loja();
    loja.adicionarPontos(300);
    loja.comprarItem(Loja.PALAVRA_FACIL);
    assertTrue(loja.ativarPalavraFacil());
    assertEquals(0, loja.getQuantidadePalavraFacil());
    assertTrue(loja.temPalavraFacilAtiva());
    assertTrue(loja.consumirPalavraFacilAtiva());
    assertFalse(loja.temPalavraFacilAtiva());
  }

  /**
   * Verifica que o consumo de Revelar Vogais falha quando o inventário
   * não possui nenhuma unidade disponível.
   */
  @Test
  void consumirRevelarVogaisSemItemDeveFalhar() {
    Loja loja = new Loja();
    assertFalse(loja.consumirRevelarVogais());
  }
}