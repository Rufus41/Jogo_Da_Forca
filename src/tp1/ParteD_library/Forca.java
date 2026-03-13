package tp1.ParteD_library;

public class Forca {
    private static final String[] ESTADOS = {
        """
         +---+
         |   |
             |
             |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
             |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
         |   |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
        /|   |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
        /|\\  |
             |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
        /|\\  |
        /    |
             |
        =========
        """,
        """
         +---+
         |   |
         O   |
        /|\\  |
        / \\  |
             |
        =========
        """
    };

    private final int vidasMaximas;
    private int erros;
    private boolean vidaCongelada;

    public Forca(int vidasMaximas) {
        this.vidasMaximas = vidasMaximas;
    }

    public boolean registarErro() {
        if (vidaCongelada) {
            vidaCongelada = false;
            return false;
        }

        if (erros < vidasMaximas) {
            erros++;
        }

        return true;
    }

    public boolean perdeu() {
        return erros >= vidasMaximas;
    }

    public int getErros() {
        return erros;
    }

    public int getTentativasRestantes() {
        return vidasMaximas - erros;
    }

    public String getDesenhoAtual() {
        int ultimoIndice = ESTADOS.length - 1;
        int indiceEstado = (int) Math.round((double) erros * ultimoIndice / vidasMaximas);

        return ESTADOS[Math.min(indiceEstado, ultimoIndice)];
    }

    public void ativarCongelarVida() {
        vidaCongelada = true;
    }

    public boolean temVidaCongelada() {
        return vidaCongelada;
    }
}
