package tp1.tests_ParteD;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tp1.ParteD_library.Jogo;

class JogoPontuacaoTest {
    @Test
    void pontuacaoBaseDeveDependerDoValorBaseDaDificuldade() {
        assertEquals(30.0, Jogo.calcularPontuacaoBase(3, 10), 0.001);
        assertEquals(45.0, Jogo.calcularPontuacaoBase(3, 15), 0.001);
        assertEquals(60.0, Jogo.calcularPontuacaoBase(3, 20), 0.001);
    }

    @Test
    void multiplicadoresDevemVariarConformeOTurno() {
        assertEquals(3.0, Jogo.getMultiplicador(1), 0.001);
        assertEquals(2.0, Jogo.getMultiplicador(2), 0.001);
        assertEquals(1.5, Jogo.getMultiplicador(3), 0.001);
        assertEquals(1.0, Jogo.getMultiplicador(4), 0.001);
        assertEquals(1.0, Jogo.getMultiplicador(8), 0.001);
    }

    @Test
    void pontuacaoDaPalavraCompletaDeveAplicarOMultiplicadorCorreto() {
        assertEquals(150.0, Jogo.calcularPontuacaoPalavraCompleta(5, 15, 2), 0.001);
        assertEquals(300.0, Jogo.calcularPontuacaoPalavraCompleta(5, 20, 1), 0.001);
        assertEquals(150.0, Jogo.calcularPontuacaoPalavraCompleta(5, 20, 3), 0.001);
        assertEquals(100.0, Jogo.calcularPontuacaoPalavraCompleta(5, 20, 4), 0.001);
    }
}