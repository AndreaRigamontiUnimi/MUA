package mua;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import utils.ASCIICharSequence;
import utils.DateEncoding;

/** Classe immutabile che rappresenta la data in un messaggio di posta */
public class Data implements Comparable<Data>, Intestazione {
  /** Il tipo relativo all'intestazione */
  private final String tipo = "Date";

  /** Il valore relativo alla Data */
  private final ZonedDateTime valore;

  /*
   * AF:
   *   rappresenta una data di una mail
   *   Date: valore -> dove valore è rappresentato dall'RFC 1123
   * RI:
   *   valore non deve essere null
   * */

  /**
   * Costruttore dell'intestazione Data
   *
   * @param valore il valore di questa intestazione
   * @throws DateTimeParseException se la stringa in input è formattata male
   * @throws IllegalArgumentException se la stringa in input è null o non è ASCII
   */
  public Data(final String valore) throws DateTimeParseException, IllegalArgumentException {
    if (valore == null || !ASCIICharSequence.isAscii(valore))
      throw new IllegalArgumentException("La data è null oppure non è in caratteri ascii");
    this.valore = DateEncoding.decode(ASCIICharSequence.of(valore));
  }

  @Override
  public String tipo() {
    return tipo;
  }

  @Override
  public String valore() {
    return DateEncoding.encode(valore).toString();
  }

  @Override
  public String toString() {
    return tipo + ": " + DateEncoding.encode(valore).toString();
  }

  @Override
  public int compareTo(Data o) {
    return valore.compareTo(o.valore);
  }

  /**
   * Metodo che restituisce una parte formattata in questo modo:
   *
   * <p>aaaa(anno)-mm(mese)-gg(giorno)
   *
   * <p>hh(ore):mm(minuti):s(secondi)
   *
   * @return una stringa secondo il formato descritto in precedenza
   */
  public String formattaData() {
    return valore.getYear()
        + "-"
        + (valore.getMonthValue() >= 10 ? valore.getMonthValue() : "0" + valore.getMonthValue())
        + "-"
        + (valore.getDayOfMonth() >= 10 ? valore.getDayOfMonth() : "0" + valore.getDayOfMonth())
        + "\n"
        + (valore.getHour() >= 10 ? valore.getHour() : "0" + valore.getHour())
        + ":"
        + (valore.getMinute() >= 10 ? valore.getMinute() : "0" + valore.getMinute())
        + ":"
        + (valore.getSecond() >= 10 ? valore.getSecond() : "0" + valore.getSecond());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Data altro) {
      return altro.valore.equals(this.valore);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return valore.hashCode();
  }
}
