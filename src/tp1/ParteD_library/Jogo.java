package tp1.ParteD_library;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Jogo {
    private final GestorPalavras gestorPalavras;
    private final Scanner scanner;
    private final Random random = new Random();

    public Jogo(GestorPalavras gestorPalavras, Scanner scanner) {
        this.gestorPalavras = gestorPalavras;
        this.scanner = scanner;
    }

    public HistoricoRonda jogar(String categoria, String dificuldade, int vidas, int valorBase, Loja loja) {
        Palavra palavra = gestorPalavras.sortearPalavra(categoria, loja.consumirPalavraFacilAtiva());
        Forca forca = new Forca(vidas);
        Set<Character> letrasTentadas = new LinkedHashSet<>();
        boolean acertouPalavraCompleta = false;
        boolean falhouPalavraCompleta = false;
        int letrasCorretas = 0;
        int turnoAtual = 1;
        double pontuacaoFinal = 0;

        while (!palavra.estaDescoberta() && !forca.perdeu() && !falhouPalavraCompleta) {
            double pontuacaoAtual = calcularPontuacaoBase(letrasCorretas, valorBase);
            mostrarEstado(palavra, forca, letrasTentadas, categoria, dificuldade, turnoAtual, pontuacaoAtual, loja);
            String acao = lerAcao();

            if ("L".equals(acao)) {
                char letra = lerLetra(letrasTentadas);
                letrasTentadas.add(letra);

                int letrasReveladas = palavra.revelarLetra(letra);

                if (letrasReveladas > 0) {
                    letrasCorretas += letrasReveladas;
                    pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
                    System.out.println(ConsolaUtils.sucesso("Acertaste na letra."));
                } else {
                    boolean perdeuVida = forca.registarErro();
                    if (perdeuVida) {
                        System.out.println(ConsolaUtils.erro("Letra errada."));
                    } else {
                        System.out.println(ConsolaUtils.sucesso("A vida congelada protegeu-te deste erro."));
                    }
                }
            } else if ("P".equals(acao)) {
                String tentativa = lerPalavraCompleta();
                if (palavra.acertouPalavraCompleta(tentativa)) {
                    acertouPalavraCompleta = true;
                    palavra.revelarTudo();
                    pontuacaoFinal = calcularPontuacaoPalavraCompleta(palavra.getNumeroDeLetras(), valorBase, turnoAtual);
                    System.out.println(ConsolaUtils.sucesso("Acertaste na palavra completa."));
                    break;
                }

                pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
                falhouPalavraCompleta = true;
                System.out.println(ConsolaUtils.erro("Palavra errada. Perdeste o jogo."));
            } else {
                int letrasReveladas = usarItem(acao, palavra, forca, loja);

                if (letrasReveladas > 0) {
                    letrasCorretas += letrasReveladas;
                    pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
                }

                if (palavra.estaDescoberta()) {
                    pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
                }

                System.out.println();
                continue;
            }

            if (!palavra.estaDescoberta() && !forca.perdeu() && !falhouPalavraCompleta) {
                turnoAtual++;
            }

            System.out.println();
        }

        if (palavra.estaDescoberta() && !acertouPalavraCompleta) {
            pontuacaoFinal = calcularPontuacaoBase(letrasCorretas, valorBase);
        }

        mostrarEstado(palavra, forca, letrasTentadas, categoria, dificuldade, turnoAtual, pontuacaoFinal, loja);

        String resultado;
        if (palavra.estaDescoberta() || acertouPalavraCompleta) {
            resultado = "Vitoria";
            System.out.println(ConsolaUtils.sucesso("Ganhaste. A palavra era " + palavra.getTextoOriginal() + "."));
        } else {
            resultado = "Derrota";
            System.out.println(ConsolaUtils.erro("Perdeste. A palavra era " + palavra.getTextoOriginal() + "."));
        }
        System.out.println(ConsolaUtils.neutro("Pontuacao final: " + formatarPontuacao(pontuacaoFinal)));

        return new HistoricoRonda(
                palavra.getTextoOriginal(),
                categoria,
                dificuldade,
                resultado,
                pontuacaoFinal,
                turnoAtual);
    }

    private void mostrarEstado(Palavra palavra, Forca forca, Set<Character> letrasTentadas,
            String categoria, String dificuldade, int turnoAtual, double pontuacaoAtual, Loja loja) {
        System.out.println(ConsolaUtils.titulo("JOGO DA FORCA"));
        System.out.println(ConsolaUtils.menu("Categoria: " + categoria));
        System.out.println(ConsolaUtils.menu("Dificuldade: " + dificuldade));
        System.out.println(ConsolaUtils.neutro("Turno: " + turnoAtual));
        System.out.println(forca.getDesenhoAtual());
        System.out.println(ConsolaUtils.menu("Palavra: " + palavra.getProgressoFormatado()));
        System.out.println(ConsolaUtils.neutro("Letras tentadas: " + formatarLetrasTentadas(letrasTentadas)));
        System.out.println(ConsolaUtils.neutro(
                "Erros: " + forca.getErros() + " | Tentativas restantes: " + forca.getTentativasRestantes()));
        System.out.println(ConsolaUtils.neutro("Vida congelada: " + (forca.temVidaCongelada() ? "ativa" : "nao")));
        System.out.println(ConsolaUtils.neutro("Pontuacao atual: " + formatarPontuacao(pontuacaoAtual)));
        System.out.println(ConsolaUtils.menu("[L] Tentar letra"));
        System.out.println(ConsolaUtils.menu("[P] Adivinhar palavra completa"));
        System.out.println(formatarAtalhoItem("1", "Dica", loja.getQuantidadeDicas()));
        System.out.println(formatarAtalhoItem("2", "Congelar Vida", loja.getQuantidadeCongelarVida()));
        System.out.println(formatarAtalhoItem("3", "Revelar Vogais", loja.getQuantidadeRevelarVogais()));
        System.out.println(formatarAtalhoItem("4", "Palavra Facil", loja.getQuantidadePalavraFacil()));
        System.out.println();
    }

    public static double calcularPontuacaoBase(int letrasCorretas, int valorBase) {
        return letrasCorretas * valorBase;
    }

    public static double getMultiplicador(int turnoAtual) {
        if (turnoAtual == 1) {
            return 3.0;
        }
        if (turnoAtual == 2) {
            return 2.0;
        }
        if (turnoAtual == 3) {
            return 1.5;
        }
        return 1.0;
    }

    public static double calcularPontuacaoPalavraCompleta(int numeroLetras, int valorBase, int turnoAtual) {
        return numeroLetras * valorBase * getMultiplicador(turnoAtual);
    }

    private String lerAcao() {
        while (true) {
            System.out.print(ConsolaUtils.menu("Escolhe uma acao: "));
            String input = scanner.nextLine().trim().toUpperCase();

            if ("L".equals(input) || "P".equals(input)
                    || "1".equals(input) || "2".equals(input)
                    || "3".equals(input) || "4".equals(input)) {
                return input;
            }

            System.out.println(ConsolaUtils.erro("Escolhe L, P ou um dos atalhos 1-4."));
        }
    }

    private char lerLetra(Set<Character> letrasTentadas) {
        while (true) {
            System.out.print(ConsolaUtils.menu("Introduz uma letra: "));
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println(ConsolaUtils.erro("Introduz apenas uma letra valida."));
                continue;
            }

            char letra = input.charAt(0);

            if (letrasTentadas.contains(letra)) {
                System.out.println(ConsolaUtils.erro("Essa letra ja foi tentada."));
                continue;
            }

            return letra;
        }
    }

    private String lerPalavraCompleta() {
        while (true) {
            System.out.print(ConsolaUtils.menu("Introduz a palavra completa: "));
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(ConsolaUtils.erro("Introduz uma palavra valida."));
        }
    }

    private String formatarLetrasTentadas(Set<Character> letrasTentadas) {
        if (letrasTentadas.isEmpty()) {
            return "-";
        }

        StringBuilder sb = new StringBuilder();

        for (char letra : letrasTentadas) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(letra);
        }

        return sb.toString();
    }

    private String formatarPontuacao(double pontuacao) {
        if (pontuacao == Math.floor(pontuacao)) {
            return String.valueOf((int) pontuacao);
        }

        return String.format(Locale.US, "%.1f", pontuacao);
    }

    private int usarItem(String acao, Palavra palavra, Forca forca, Loja loja) {
        if ("1".equals(acao)) {
            if (!loja.consumirDica()) {
                System.out.println(ConsolaUtils.erro("Nao tens Dicas disponiveis."));
                return 0;
            }

            Character letra = palavra.getLetraOcultaAleatoria(random);
            if (letra == null) {
                System.out.println(ConsolaUtils.erro("Nao ha letras por revelar."));
                return 0;
            }

            int reveladas = palavra.revelarLetra(letra);
            System.out.println(ConsolaUtils.sucesso("A Dica revelou a letra " + letra + "."));
            return reveladas;
        }

        if ("2".equals(acao)) {
            if (!loja.consumirCongelarVida()) {
                System.out.println(ConsolaUtils.erro("Nao tens Congelar Vida disponivel."));
                return 0;
            }

            forca.ativarCongelarVida();
            System.out.println(ConsolaUtils.sucesso("Congelar Vida ativado para o proximo erro."));
            return 0;
        }

        if ("3".equals(acao)) {
            if (!loja.consumirRevelarVogais()) {
                System.out.println(ConsolaUtils.erro("Nao tens Revelar Vogais disponivel."));
                return 0;
            }

            int reveladas = palavra.revelarVogais();
            if (reveladas == 0) {
                System.out.println(ConsolaUtils.erro("Nao havia vogais por revelar."));
            } else {
                System.out.println(ConsolaUtils.sucesso("As vogais foram reveladas."));
            }
            return reveladas;
        }

        if (!loja.ativarPalavraFacil()) {
            if (loja.temPalavraFacilAtiva()) {
                System.out.println(ConsolaUtils.erro("Ja tens Palavra Facil ativa para a proxima ronda."));
            } else {
                System.out.println(ConsolaUtils.erro("Nao tens Palavra Facil disponivel."));
            }
            return 0;
        }

        System.out.println(ConsolaUtils.sucesso("Palavra Facil ativada para a proxima ronda."));
        return 0;
    }

    private String formatarAtalhoItem(String atalho, String nome, int quantidade) {
        if (quantidade <= 0) {
            return ConsolaUtils.desativado("[" + atalho + "] " + nome + " (tens: 0) - indisponivel");
        }

        return ConsolaUtils.menu("[" + atalho + "] " + nome + " (tens: " + quantidade + ")");
    }
}
