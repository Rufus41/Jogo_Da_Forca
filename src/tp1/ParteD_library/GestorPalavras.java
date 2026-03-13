package tp1.ParteD_library;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GestorPalavras {
    private static final Map<String, String[]> CATEGORIAS = new LinkedHashMap<>();
    private final Random random = new Random();

    static {
        CATEGORIAS.put("Animais", new String[] {
            "ELEFANTE", "TIGRE", "COELHO", "GIRAFA", "CANGURU",
            "LEOPARDO", "GOLFINHO", "PANTERA", "TARTARUGA", "RINOCERONTE",
            "URSO PARDO", "LOBO MARINHO", "AGUIA REAL", "FOCA CINZENTA",
            "CAVALO MARINHO", "PANDA VERMELHO", "LINCE IBERICO", "ORCA",
            "CAMELO", "HIPOPOTAMO"
        });
        CATEGORIAS.put("Paises", new String[] {
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

    public Palavra sortearPalavra(String categoria, boolean palavraFacil) {
        String[] palavras = CATEGORIAS.get(categoria);

        if (palavras == null) {
            throw new IllegalArgumentException("Categoria invalida: " + categoria);
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

    public String[] getCategoriasDisponiveis() {
        return CATEGORIAS.keySet().toArray(new String[0]);
    }

    public String[] getPalavrasDaCategoria(String categoria) {
        String[] palavras = CATEGORIAS.get(categoria);

        if (palavras == null) {
            throw new IllegalArgumentException("Categoria invalida: " + categoria);
        }

        return palavras.clone();
    }

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
