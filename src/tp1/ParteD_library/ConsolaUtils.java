package tp1.ParteD_library;

import java.io.IOException;

public final class ConsolaUtils {
    private static final String RESET = "\u001B[0m";
    private static final String VERDE = "\u001B[32m";
    private static final String VERMELHO = "\u001B[31m";
    private static final String AMARELO = "\u001B[33m";
    private static final String CIANO = "\u001B[36m";
    private static final String CINZENTO = "\u001B[90m";

    private ConsolaUtils() {
    }

    public static void limparEcra() {
        try {
            if (isWindows()) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static String titulo(String texto) {
        return CIANO + texto + RESET;
    }

    public static String menu(String texto) {
        return CIANO + texto + RESET;
    }

    public static String sucesso(String texto) {
        return VERDE + texto + RESET;
    }

    public static String erro(String texto) {
        return VERMELHO + texto + RESET;
    }

    public static String neutro(String texto) {
        return AMARELO + texto + RESET;
    }

    public static String desativado(String texto) {
        return CINZENTO + texto + RESET;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
