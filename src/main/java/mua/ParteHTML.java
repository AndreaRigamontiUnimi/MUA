package mua;

/**
 * Classe mutabile che implementa una parte di un messaggio nel formato HTML Estende la classe
 * {@link Parte}
 */
public class ParteHTML extends Parte {
  /*
   * AF:
   *   AF(super)
   *   Le intestazioni che deve avere sono:
   *          - Intestazione.HTML_INTESTAZIONE
   *          - Intestazione.INTESTAZIONE_TRANSFER_ENCODING
   * RI:
   *   RI(super)
   * */

  /**
   * Costruttore di una parte di messaggio formato HTML
   *
   * @param s la stringa che sarà il campo s di questa parte di messaggio
   * @throws NullPointerException se s è null
   */
  public ParteHTML(String s) throws NullPointerException {
    super(new CorpoNonASCII(s));
  }
}
