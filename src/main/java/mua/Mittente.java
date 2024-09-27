package mua;

import java.util.List;

/** Classe immutabile che rappresenta il mittente di un messaggio di posta */
public class Mittente implements Intestazione {
  /** Campo che identifica il tipo dell'intestazione */
  private final String tipo = "From";

  /** Campo che identifica l'indirizzo del Mittente */
  private final Indirizzo valore;

  /*
   *
   * AF:
   *   contiene le informazioni del mittente
   * RI:
   *   valore non deve essere null
   * */

  /**
   * Costruttore di un mittente
   *
   * @param nome il nome del mittente
   * @param locale il locale del mittente
   * @param dominio il dominio del mittente
   * @throws IllegalArgumentException se locale e dominio non rispettano la codifica
   */
  public Mittente(final String nome, final String dominio, final String locale)
      throws IllegalArgumentException {
    if (nome == null) valore = new Indirizzo(locale, dominio);
    else valore = new Indirizzo(nome, locale, dominio);
  }

  @Override
  public String valore() {
    return valore.toString();
  }

  @Override
  public String tipo() {
    return tipo;
  }

  /**
   * Metodo statico che mi permette di decodificare una stringa di un mittente
   *
   * <p>Precondizioni: la stringa in input deve essere in questo formato: From: Nome Utente
   * nome@dominio.it
   *
   * <p>Dove la parte a destra dei due punti deve essere formattata come descritto da un {@link
   * Indirizzo}
   *
   * @param s la stringa da decodificare
   * @return una lista di stringhe che corrisponde alla decodifica di s
   * @throws IllegalArgumentException se il formato della stringa dopo i ":" è in codifica ASCII
   */
  public static List<String> decodifica(String s) throws IllegalArgumentException {
    String[] splitted = s.split(": ");
    return Indirizzo.decodifica(splitted[1]);
  }

  @Override
  public String toString() {
    return tipo + ": " + valore;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Mittente m) return m.valore.equals(this.valore);
    return false;
  }

  @Override
  public int hashCode() {
    return valore.hashCode();
  }
}
