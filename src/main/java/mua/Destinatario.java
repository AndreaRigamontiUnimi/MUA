package mua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import utils.ASCIICharSequence;
import utils.AddressEncoding;

/** Classe mutabile che rappresenta il destinatario di un messaggio di posta */
public class Destinatario implements Iterable<Indirizzo>, Intestazione {
  /** Campo che identifica il tipo dell'intestazione */
  private final String tipo = "To";

  /** Campo che identifica l'indirizzo del Destinatario */
  private final List<Indirizzo> valore = new ArrayList<>();

  /*
   *
   * AF:
   *   contiene le informazioni del destinatario
   * RI:
   *   valore non deve essere null
   */

  /**
   * Metodo che mi permette di aggiungere un destinatario alla lista dei destinatari
   *
   * @param nome il nome del destinatario da aggiungere
   * @param locale il locale del destinatario da aggiungere
   * @param dominio il dominio del destinatario da aggiungere
   * @throws IllegalArgumentException se locale o dominio non rispettano la codifica
   */
  public void aggiungiDestinatario(final String nome, final String dominio, final String locale)
      throws IllegalArgumentException {
    valore.add(
        nome == null ? new Indirizzo(locale, dominio) : new Indirizzo(nome, locale, dominio));
  }

  @Override
  public String valore() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < valore.size() - 1; i++) {
      sb.append(valore.get(i)).append(", ");
    }
    return sb.append(valore.get(valore.size() - 1)).toString();
  }

  @Override
  public String tipo() {
    return tipo;
  }

  /**
   * Metodo statico che permette la decodifica dei una stringa di un destinatario
   *
   * <p>vedi {@link AddressEncoding}.decode(..) per il formato dell'input
   *
   * @param s la stringa in input da decodificare
   * @return una lista di liste contenenti una tripla di stringhe, corrispondente alla decodifica
   *     del destinatario
   */
  public static List<List<String>> decodifica(String s) {
    String[] splitted = s.split(": ");
    return AddressEncoding.decode(ASCIICharSequence.of(splitted[1]));
  }

  @Override
  public Iterator<Indirizzo> iterator() {
    return Collections.unmodifiableList(valore).iterator();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(tipo);
    sb.append(": ");
    for (int i = 0; i < valore.size() - 1; i++) {
      sb.append(valore.get(i)).append(", ");
    }
    return sb.append(valore.get(valore.size() - 1)).toString();
  }

  /**
   * Metodo che mi restituisce il destinatario formattato:
   *
   * <p>d1
   *
   * <p>d2
   *
   * <p>...
   *
   * <p>dn
   *
   * @return la formattazione della stringa
   */
  public String formattaDestinatario() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < valore.size(); i++) {
      List<String> d = Indirizzo.decodifica(valore.get(i).toString());
      sb.append(d.get(2)).append("@").append(d.get(1));
      if (i != valore.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Destinatario altro) {
      if (altro.valore.size() != valore.size()) return false;
      for (int i = 0; i < valore.size(); i++) {
        if (!valore.get(i).equals(altro.valore.get(i))) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(valore);
  }
}
