package tp1;

import tp1.ParteD_library.Menu;

/**
 * Classe principal do jogo da Forca.
 *
 * <p>Esta classe constitui o ponto de entrada da aplicação, instanciando
 * o {@link Menu} e iniciando o fluxo principal do jogo.</p>
 *
 * @see Menu
 */
public class ParteD_Jogo_da_Forca {

  /**
   * Método principal que inicia o jogo da Forca.
   *
   * <p>Cria uma instância de {@link Menu} e invoca o método
   * {@link Menu#iniciar()}, que ficará responsável por controlar
   * o fluxo principal do programa.</p>
   *
   * @param args argumentos da linha de comando (não utilizados)
   */
  public static void main(String[] args) {
    Menu menu = new Menu();
    menu.iniciar();
  }
}