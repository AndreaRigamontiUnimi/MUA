package mua;

/**
 * Classe mutabile che implementa una parte di un messaggio contenente le informazioni del messaggio
 * Estende la classe {@link Parte}
 */
public class ParteInformazioniMessaggio extends Parte {
  /*
   * AF:
   *   AF(super)
   *   Le intestazioni che deve avere sono:
   *      - Mittente
   *      - Destinatario
   *      - Oggetto
   *      - Data
   * RI:
   *   RI(super)
   * */

  /**
   * Costruisce una parte per le informazioni del messaggio con corpo vuoto
   *
   * @throws NullPointerException se il corpo passato al costruttore è null
   */
  public ParteInformazioniMessaggio() throws NullPointerException {
    super(new CorpoASCII(""));
  }
}
