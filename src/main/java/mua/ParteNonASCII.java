package mua;

/**
 * Classe mutabile che implementa una parte di un messaggio che non contiene solo caratteri Ascii
 * Estende la classe {@link Parte}
 */
public class ParteNonASCII extends Parte {

  /*
   * AF:
   *   AF(super)
   *   Le intestazioni che deve avere sono:
   *      - Intestazione.NON_ASCII_INTESTAZIONE
   *      - Intestazione.INTESTAZIONE_TRANSFER_ENCODING
   * RI:
   *   RI(super)
   * */

  /**
   * Costruttore di una parte di messaggio contenente non solo caratteri ASCII
   *
   * @param s la stringa sarà il campo s di questa classe
   * @throws NullPointerException se <b> s </b> è null
   */
  public ParteNonASCII(String s) throws NullPointerException {
    super(new CorpoNonASCII(s));
  }
}
