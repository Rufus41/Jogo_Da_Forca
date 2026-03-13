package tp1.ParteD_library;

import java.util.Locale;

public class Loja {
    public static final int DICA = 1;
    public static final int CONGELAR_VIDA = 2;
    public static final int REVELAR_VOGAIS = 3;
    public static final int PALAVRA_FACIL = 4;

    private static final int CUSTO_DICA = 50;
    private static final int CUSTO_CONGELAR_VIDA = 150;
    private static final int CUSTO_REVELAR_VOGAIS = 200;
    private static final int CUSTO_PALAVRA_FACIL = 250;

    private double saldo;
    private int quantidadeDicas;
    private int quantidadeCongelarVida;
    private int quantidadeRevelarVogais;
    private int quantidadePalavraFacil;
    private boolean proximaPalavraFacil;

    public void adicionarPontos(double pontos) {
        saldo += pontos;
    }

    public boolean comprarItem(int item) {
        int custo = getCustoItem(item);

        if (saldo < custo) {
            return false;
        }

        saldo -= custo;

        if (item == DICA) {
            quantidadeDicas++;
        } else if (item == CONGELAR_VIDA) {
            quantidadeCongelarVida++;
        } else if (item == REVELAR_VOGAIS) {
            quantidadeRevelarVogais++;
        } else if (item == PALAVRA_FACIL) {
            quantidadePalavraFacil++;
        } else {
            saldo += custo;
            return false;
        }

        return true;
    }

    public boolean consumirDica() {
        if (quantidadeDicas <= 0) {
            return false;
        }

        quantidadeDicas--;
        return true;
    }

    public boolean consumirCongelarVida() {
        if (quantidadeCongelarVida <= 0) {
            return false;
        }

        quantidadeCongelarVida--;
        return true;
    }

    public boolean consumirRevelarVogais() {
        if (quantidadeRevelarVogais <= 0) {
            return false;
        }

        quantidadeRevelarVogais--;
        return true;
    }

    public boolean ativarPalavraFacil() {
        if (quantidadePalavraFacil <= 0 || proximaPalavraFacil) {
            return false;
        }

        quantidadePalavraFacil--;
        proximaPalavraFacil = true;
        return true;
    }

    public boolean consumirPalavraFacilAtiva() {
        boolean ativa = proximaPalavraFacil;
        proximaPalavraFacil = false;
        return ativa;
    }

    public boolean temPalavraFacilAtiva() {
        return proximaPalavraFacil;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getQuantidadeDicas() {
        return quantidadeDicas;
    }

    public int getQuantidadeCongelarVida() {
        return quantidadeCongelarVida;
    }

    public int getQuantidadeRevelarVogais() {
        return quantidadeRevelarVogais;
    }

    public int getQuantidadePalavraFacil() {
        return quantidadePalavraFacil;
    }

    public int getCustoItem(int item) {
        if (item == DICA) {
            return CUSTO_DICA;
        }
        if (item == CONGELAR_VIDA) {
            return CUSTO_CONGELAR_VIDA;
        }
        if (item == REVELAR_VOGAIS) {
            return CUSTO_REVELAR_VOGAIS;
        }
        if (item == PALAVRA_FACIL) {
            return CUSTO_PALAVRA_FACIL;
        }
        return Integer.MAX_VALUE;
    }

    public String formatarSaldo() {
        if (saldo == Math.floor(saldo)) {
            return String.valueOf((int) saldo);
        }

        return String.format(Locale.US, "%.1f", saldo);
    }
}
