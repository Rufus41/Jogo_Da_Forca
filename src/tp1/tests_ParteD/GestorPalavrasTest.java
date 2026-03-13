package tp1.tests_ParteD;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tp1.ParteD_library.GestorPalavras;
import tp1.ParteD_library.Palavra;

class GestorPalavrasTest {
    @Test
    void categoriasPrincipaisDevemExistir() {
        GestorPalavras gestor = new GestorPalavras();
        String[] categorias = gestor.getCategoriasDisponiveis();
        assertTrue(Arrays.asList(categorias).contains("Animais"));
        assertTrue(Arrays.asList(categorias).contains("Paises"));
        assertTrue(Arrays.asList(categorias).contains("Desportos"));
        assertTrue(Arrays.asList(categorias).contains("Tecnologia"));
    }

    @Test
    void categoriasDevemConterExpressoesComEspacos() {
        GestorPalavras gestor = new GestorPalavras();
        assertTrue(Arrays.stream(gestor.getPalavrasDaCategoria("Desportos")).anyMatch(p -> p.contains(" ")));
        assertTrue(Arrays.stream(gestor.getPalavrasDaCategoria("Tecnologia")).anyMatch(p -> p.contains(" ")));
    }

    @Test
    void filtroDePalavraFacilDeveSortearApenasPalavrasComSeisLetrasOuMenos() {
        GestorPalavras gestor = new GestorPalavras();
        for (int i = 0; i < 20; i++) {
            Palavra palavra = gestor.sortearPalavra("Comida", true);
            assertTrue(palavra.getNumeroDeLetras() <= 6);
        }
    }

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
