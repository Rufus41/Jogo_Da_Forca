package tp1.ParteD_library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final String[] DIFICULDADES = { "Facil", "Medio", "Dificil" };
    private static final int[] VIDAS = { 8, 6, 4 };
    private static final int[] VALORES_BASE = { 10, 15, 20 };

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        GestorPalavras gestorPalavras = new GestorPalavras();
        Jogo jogo = new Jogo(gestorPalavras, scanner);
        Loja loja = new Loja();
        List<HistoricoRonda> historico = new ArrayList<>();
        boolean sair = false;

        while (!sair) {
            ConsolaUtils.limparEcra();
            mostrarMenuPrincipal(loja);
            int opcao = lerOpcao(scanner, 5);

            if (opcao == 1) {
                jogarNovaRonda(scanner, gestorPalavras, jogo, historico, loja);
            } else if (opcao == 2) {
                abrirLoja(scanner, loja);
            } else if (opcao == 3) {
                ConsolaUtils.limparEcra();
                mostrarHistorico(historico);
                esperarVoltarAoMenu(scanner);
            } else if (opcao == 4) {
                ConsolaUtils.limparEcra();
                mostrarRecorde(historico);
                esperarVoltarAoMenu(scanner);
            } else {
                sair = true;
                ConsolaUtils.limparEcra();
                System.out.println(ConsolaUtils.sucesso("Programa terminado."));
            }

            System.out.println();
        }
    }

    private void mostrarMenuPrincipal(Loja loja) {
        System.out.println(ConsolaUtils.titulo("JOGO DA FORCA"));
        System.out.println(ConsolaUtils.neutro("Saldo atual: " + loja.formatarSaldo()));
        if (loja.temPalavraFacilAtiva()) {
            System.out.println(ConsolaUtils.sucesso("Palavra Facil ativa para a proxima ronda."));
        }
        System.out.println(ConsolaUtils.menu("1 - Jogar"));
        System.out.println(ConsolaUtils.menu("2 - Loja"));
        System.out.println(ConsolaUtils.menu("3 - Ver historico"));
        System.out.println(ConsolaUtils.menu("4 - Ver recorde"));
        System.out.println(ConsolaUtils.menu("5 - Sair"));
    }

    private void jogarNovaRonda(Scanner scanner, GestorPalavras gestorPalavras, Jogo jogo,
            List<HistoricoRonda> historico, Loja loja) {
        String categoria = escolherCategoria(scanner, gestorPalavras);
        int indiceDificuldade = escolherDificuldade(scanner);

        ConsolaUtils.limparEcra();
        System.out.println(ConsolaUtils.sucesso("Categoria escolhida: " + categoria));
        System.out.println(ConsolaUtils.sucesso("Dificuldade escolhida: " + DIFICULDADES[indiceDificuldade]
                + " (" + VIDAS[indiceDificuldade] + " vidas)"));
        System.out.println();

        HistoricoRonda ronda = jogo.jogar(
                categoria,
                DIFICULDADES[indiceDificuldade],
                VIDAS[indiceDificuldade],
                VALORES_BASE[indiceDificuldade],
                loja);

        historico.add(ronda);
        loja.adicionarPontos(ronda.getPontuacao());
        esperarVoltarAoMenu(scanner);
    }

    private void abrirLoja(Scanner scanner, Loja loja) {
        boolean voltar = false;

        while (!voltar) {
            ConsolaUtils.limparEcra();
            mostrarMenuLoja(loja);
            int opcao = lerOpcao(scanner, 5);

            if (opcao == 5) {
                voltar = true;
            } else if (loja.comprarItem(opcao)) {
                System.out.println(ConsolaUtils.sucesso("Compra efetuada com sucesso."));
            } else {
                System.out.println(ConsolaUtils.erro("Nao tens pontos suficientes para essa compra."));
            }

            System.out.println();
            if (!voltar) {
                esperarContinuar(scanner);
            }
        }
    }

    private void mostrarMenuLoja(Loja loja) {
        System.out.println(ConsolaUtils.titulo("LOJA"));
        System.out.println(ConsolaUtils.neutro("Saldo atual: " + loja.formatarSaldo()));
        System.out.println(ConsolaUtils.menu("1 - Dica (" + loja.getCustoItem(Loja.DICA)
                + " pts) | tens: " + loja.getQuantidadeDicas()));
        System.out.println(ConsolaUtils.menu("2 - Congelar Vida (" + loja.getCustoItem(Loja.CONGELAR_VIDA)
                + " pts) | tens: " + loja.getQuantidadeCongelarVida()));
        System.out.println(ConsolaUtils.menu("3 - Revelar Vogais (" + loja.getCustoItem(Loja.REVELAR_VOGAIS)
                + " pts) | tens: " + loja.getQuantidadeRevelarVogais()));
        System.out.println(ConsolaUtils.menu("4 - Palavra Facil (" + loja.getCustoItem(Loja.PALAVRA_FACIL)
                + " pts) | tens: " + loja.getQuantidadePalavraFacil()));
        System.out.println(ConsolaUtils.menu("5 - Voltar"));
    }

    private void mostrarHistorico(List<HistoricoRonda> historico) {
        System.out.println(ConsolaUtils.titulo("HISTORICO DA SESSAO"));

        if (historico.isEmpty()) {
            System.out.println(ConsolaUtils.neutro("Ainda nao existem rondas jogadas."));
            return;
        }

        for (int i = 0; i < historico.size(); i++) {
            System.out.println(ConsolaUtils.neutro((i + 1) + " - " + historico.get(i).toString()));
        }
    }

    private void mostrarRecorde(List<HistoricoRonda> historico) {
        System.out.println(ConsolaUtils.titulo("RECORDE DA SESSAO"));

        if (historico.isEmpty()) {
            System.out.println(ConsolaUtils.neutro("Ainda nao existem rondas jogadas."));
            return;
        }

        HistoricoRonda melhorRonda = historico.get(0);

        for (HistoricoRonda ronda : historico) {
            if (ronda.getPontuacao() > melhorRonda.getPontuacao()) {
                melhorRonda = ronda;
            }
        }

        System.out.println(ConsolaUtils.sucesso(melhorRonda.toString()));
    }

    private String escolherCategoria(Scanner scanner, GestorPalavras gestorPalavras) {
        String[] categorias = gestorPalavras.getCategoriasDisponiveis();

        ConsolaUtils.limparEcra();
        System.out.println(ConsolaUtils.titulo("ESCOLHER CATEGORIA"));
        for (int i = 0; i < categorias.length; i++) {
            System.out.println(ConsolaUtils.menu((i + 1) + " - " + categorias[i]));
        }

        int opcao = lerOpcao(scanner, categorias.length);
        return categorias[opcao - 1];
    }

    private int escolherDificuldade(Scanner scanner) {
        ConsolaUtils.limparEcra();
        System.out.println(ConsolaUtils.titulo("ESCOLHER DIFICULDADE"));
        for (int i = 0; i < DIFICULDADES.length; i++) {
            System.out.println(ConsolaUtils.menu((i + 1) + " - " + DIFICULDADES[i] + " (" + VIDAS[i] + " vidas)"));
        }

        return lerOpcao(scanner, DIFICULDADES.length) - 1;
    }

    private int lerOpcao(Scanner scanner, int maximo) {
        while (true) {
            System.out.print(ConsolaUtils.menu("Opcao: "));
            String input = scanner.nextLine().trim();

            try {
                int opcao = Integer.parseInt(input);
                if (opcao >= 1 && opcao <= maximo) {
                    return opcao;
                }
            } catch (NumberFormatException e) {
                // Mantem o fluxo de validacao no aviso abaixo.
            }

            System.out.println(ConsolaUtils.erro("Escolhe uma opcao valida."));
        }
    }

    private void esperarVoltarAoMenu(Scanner scanner) {
        System.out.print(ConsolaUtils.menu("Prime Enter para voltar ao menu..."));
        scanner.nextLine();
    }

    private void esperarContinuar(Scanner scanner) {
        System.out.print(ConsolaUtils.menu("Prime Enter para continuar..."));
        scanner.nextLine();
    }
}
