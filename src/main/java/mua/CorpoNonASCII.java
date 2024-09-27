package mua;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/** Classe concreta che implementa un corpo che non contiene solo caratteri ascii */
public class CorpoNonASCII implements Corpo {

  /** Stringa che contiene il testo di una parte di messaggio */
  private final String s;

  /*
   * AF:
   *   è la stringa s stessa
   * RI:
   *   s non deve essere null
   * */

  /**
   * Costruttore di un corpo fatto da non soli caratteri ASCII
   *
   * @param s il testo contenuto nel corpo
   * @throws NullPointerException se il parametro in input è null
   */
  public CorpoNonASCII(final String s) throws NullPointerException {
    this.s = Objects.requireNonNull(s);
  }

  @Override
  public String codifica() {
    return Base64.getMimeEncoder(0, new byte[0])
        .encodeToString(Objects.requireNonNull(s).getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String decodifica() {
    return s;
  }
}
