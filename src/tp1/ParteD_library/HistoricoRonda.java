package tp1.ParteD_library;

import java.util.Locale;

public class HistoricoRonda {
    private final String palavra;
    private final String categoria;
    private final String dificuldade;
    private final String resultado;
    private final double pontuacao;
    private final int numeroTurnos;

    public HistoricoRonda(String palavra, String categoria, String dificuldade,
            String resultado, double pontuacao, int numeroTurnos) {
        this.palavra = palavra;
        this.categoria = categoria;
        this.dificuldade = dificuldade;
        this.resultado = resultado;
        this.pontuacao = pontuacao;
        this.numeroTurnos = numeroTurnos;
    }

    public double getPontuacao() {
        return pontuacao;
    }

    @Override
    public String toString() {
        return "Palavra: " + palavra
                + " | Categoria: " + categoria
                + " | Dificuldade: " + dificuldade
                + " | Resultado: " + resultado
                + " | Pontuacao: " + formatarPontuacao(pontuacao)
                + " | Turnos: " + numeroTurnos;
    }

    private String formatarPontuacao(double valor) {
        if (valor == Math.floor(valor)) {
            return String.valueOf((int) valor);
        }

        return String.format(Locale.US, "%.1f", valor);
    }
}
