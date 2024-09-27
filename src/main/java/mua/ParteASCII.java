package mua;

/**
 * Classe mutabile che implementa una parte di un messaggio che contiene solo caratteri Ascii
 * Estende la classe {@link Parte}
 */
public class ParteASCII extends Parte {
  /*
   * AF:
   *   AF(super)
   *   L'intestazione che deve avere è: Intestazione.ASCII_INTESTAZIONE
   * RI:
   *   RI(super)
   * */

  /**
   * Costruttore di una parte di messaggio contenente solo caratteri ASCII
   *
   * @param s la stringa nel corpo
   * @throws NullPointerException se s è null
   */
  public ParteASCII(String s) throws NullPointerException {
    super(new CorpoASCII(s));
  }
}
