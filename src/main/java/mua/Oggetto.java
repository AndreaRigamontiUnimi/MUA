package mua;

import utils.ASCIICharSequence;
import utils.Base64Encoding;

/** Classe immutabile che rappresenta l'oggetto di un messaggio di posta */
public class Oggetto implements Intestazione {

  /** Campo che identifica il tipo dell'intestazione */
  private final String tipo = "Subject";

  /** Il valore relativo all'oggetto' */
  private final String valore;

  /*
   *AF:
   *   rappresenta una data di una mail
   *   Date: valore -> dove valore è una stringa che rappresenta il contenuto
   *                   dell'oggetto della mail
   * RI:
   *   valore non deve essere null
   */

  /**
   * Costruttore di un'intestazione di tipo oggetto
   *
   * @param valore la stringa da assegnare al valore dell'oggetto
   * @throws IllegalArgumentException se la stringa in input è null\
   */
  public Oggetto(final String valore) throws IllegalArgumentException {
    if (valore != null) this.valore = valore;
    else throw new IllegalArgumentException("Il valore dell'oggetto non può essere null");
  }

  @Override
  public String tipo() {
    return tipo;
  }

  @Override
  public String valore() {
    return codifica();
  }

  /**
   * Metodo che permette di fare la codifica del valore dell'oggetto
   *
   * <p>Se il valore è già composto da caratteri ASCII, non viene codificato
   *
   * @return la codifica in Base64 dell'oggetto
   */
  public String codifica() {
    return !ASCIICharSequence.isAscii(valore) ? Base64Encoding.encodeWord(valore) : valore;
  }

  /**
   * Metodo statico che permette di fare la decodifica di una stringa
   *
   * <p>Se la stringa non è codificata in base64, "non viene decodificata"
   *
   * @param s la stringa di cui voglio avere la decodifica
   * @return la decodifica da Base64
   */
  public static String decodifica(String s) {
    return s.startsWith("=?utf-8?B?") && s.endsWith("?=")
        ? Base64Encoding.decodeWord(ASCIICharSequence.of(s))
        : s;
  }

  @Override
  public String toString() {
    return tipo + ": " + (ASCIICharSequence.isAscii(valore) ? valore : codifica());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Oggetto altro) {
      return altro.valore.equals(this.valore);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return valore.hashCode();
  }
}
