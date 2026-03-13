package tp1.ParteD_library;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Palavra {
    private final String textoOriginal;
    private final char[] progresso;

    public Palavra(String textoOriginal) {
        this.textoOriginal = textoOriginal.toUpperCase();
        this.progresso = new char[this.textoOriginal.length()];

        for (int i = 0; i < this.textoOriginal.length(); i++) {
            char caractere = this.textoOriginal.charAt(i);
            progresso[i] = isEspaco(caractere) ? ' ' : '_';
        }
    }

    public int revelarLetra(char letra) {
        int revelacoes = 0;

        for (int i = 0; i < textoOriginal.length(); i++) {
            if (textoOriginal.charAt(i) == letra && progresso[i] == '_') {
                progresso[i] = letra;
                revelacoes++;
            }
        }

        return revelacoes;
    }

    public boolean acertouPalavraCompleta(String tentativa) {
        return textoOriginal.equals(tentativa.trim().toUpperCase());
    }

    public void revelarTudo() {
        for (int i = 0; i < textoOriginal.length(); i++) {
            progresso[i] = textoOriginal.charAt(i);
        }
    }

    public int getNumeroDeLetras() {
        int total = 0;

        for (int i = 0; i < textoOriginal.length(); i++) {
            if (!isEspaco(textoOriginal.charAt(i))) {
                total++;
            }
        }

        return total;
    }

    public boolean estaDescoberta() {
        for (char caractere : progresso) {
            if (caractere == '_') {
                return false;
            }
        }

        return true;
    }

    public String getProgressoFormatado() {
        StringBuilder sb = new StringBuilder();

        for (char caractere : progresso) {
            sb.append(caractere).append(' ');
        }

        return sb.toString().trim();
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public Character getLetraOcultaAleatoria(Random random) {
        List<Character> letrasOcultas = new LinkedList<>();

        for (int i = 0; i < textoOriginal.length(); i++) {
            if (progresso[i] == '_' && !isEspaco(textoOriginal.charAt(i))) {
                char letra = textoOriginal.charAt(i);
                if (!letrasOcultas.contains(letra)) {
                    letrasOcultas.add(letra);
                }
            }
        }

        if (letrasOcultas.isEmpty()) {
            return null;
        }

        return letrasOcultas.get(random.nextInt(letrasOcultas.size()));
    }

    public int revelarVogais() {
        int revelacoes = 0;

        for (char vogal : new char[] { 'A', 'E', 'I', 'O', 'U' }) {
            revelacoes += revelarLetra(vogal);
        }

        return revelacoes;
    }

    private boolean isEspaco(char caractere) {
        return caractere == ' ';
    }
}
