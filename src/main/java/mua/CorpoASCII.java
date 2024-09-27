package mua;

import java.util.Objects;

/** Classe concreta che implementa un corpo costituito da soli caratteri ascii */
public class CorpoASCII implements Corpo {

  /** Stringa che contiene il testo di una parte di messaggio */
  private final String s;

  /*
   * AF:
   *   è la stringa s stessa
   * RI:
   *   s non deve essere null
   * */

  /**
   * Costruttore di un corpo fatto da soli caratteri ASCII
   *
   * @param s il testo contenuto nel corpo
   * @throws NullPointerException se il parametro in input è null
   */
  public CorpoASCII(final String s) throws NullPointerException {
    this.s = Objects.requireNonNull(s);
  }

  @Override
  public String codifica() {
    return s;
  }

  @Override
  public String decodifica() {
    return s;
  }
}
