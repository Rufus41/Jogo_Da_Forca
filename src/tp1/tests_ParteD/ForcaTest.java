package tp1.tests_ParteD;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tp1.ParteD_library.Forca;

class ForcaTest {
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
