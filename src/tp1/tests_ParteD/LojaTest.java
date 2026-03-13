package tp1.tests_ParteD;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tp1.ParteD_library.Loja;

class LojaTest {
    @Test
    void comprarItemDeveDescontarSaldoEAumentarInventario() {
        Loja loja = new Loja();
        loja.adicionarPontos(100);
        assertTrue(loja.comprarItem(Loja.DICA));
        assertEquals(50.0, loja.getSaldo(), 0.001);
        assertEquals(1, loja.getQuantidadeDicas());
    }

    @Test
    void comprarItemSemSaldoSuficienteDeveFalhar() {
        Loja loja = new Loja();
        loja.adicionarPontos(100);
        assertFalse(loja.comprarItem(Loja.REVELAR_VOGAIS));
        assertEquals(100.0, loja.getSaldo(), 0.001);
    }

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

    @Test
    void consumirRevelarVogaisSemItemDeveFalhar() {
        Loja loja = new Loja();
        assertFalse(loja.consumirRevelarVogais());
    }
}
