package tp1.tests_ParteD;

import tp1.ParteD_library.Palavra;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PalavraTest {
    @Test
    void revelarLetraDeveRevelarTodasAsOcorrencias() {
        Palavra palavra = new Palavra("BANANA");
        assertEquals(3, palavra.revelarLetra('A'));
        assertEquals("_ A _ A _ A", palavra.getProgressoFormatado());
    }

    @Test
    void letraErradaNaoDeveAlterarOProgresso() {
        Palavra palavra = new Palavra("TIGRE");
        assertEquals(0, palavra.revelarLetra('Z'));
        assertEquals("_ _ _ _ _", palavra.getProgressoFormatado());
        assertFalse(palavra.estaDescoberta());
    }

    @Test
    void expressoesDevemManterEspacosVisiveisEExigirATextoCompleto() {
        Palavra palavra = new Palavra("FUTEBOL AMERICANO");
        assertTrue(palavra.getProgressoFormatado().contains("  "));
        assertEquals(16, palavra.getNumeroDeLetras());
        assertTrue(palavra.acertouPalavraCompleta("futebol americano"));
        assertFalse(palavra.acertouPalavraCompleta("futebol"));
    }

    @Test
    void revelarVogaisDeveRevelarApenasAsVogaisPendentes() {
        Palavra palavra = new Palavra("CASA");
        assertEquals(2, palavra.revelarVogais());
        assertEquals("_ A _ A", palavra.getProgressoFormatado());
    }
}

