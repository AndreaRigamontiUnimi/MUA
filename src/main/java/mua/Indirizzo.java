package mua;

import java.util.List;
import java.util.Objects;
import utils.ASCIICharSequence;
import utils.AddressEncoding;

/** Classe immutabile che rappresenta un inirizzo Email */
public class Indirizzo {
  /** Nome del possessore dell'indirizzo */
  private final String nome;

  /** Locale dell'indirizzo */
  private final String locale;

  /** Dominio dell'indirizzo */
  private final String dominio;

  /*
   * AF:
   *   Rappresenta gli indirizzi come:
   *       se il nome è vuoto : locale@dominio
   *       altrimenti:
   *           se il nome contiene solo uno spazio : nome <locale@dominio>
   *           se il nome contiene più di uno spazio : "nome" <locale@dominio>
   * RI:
   *   locale e dominio non devono essere null e devono contenere solo caratteri ASCII
   * */

  /**
   * Costruttore che inizializza un indirizzo avendo nome, locale e dominio
   *
   * @param nome il display name
   * @param dominio il dominio
   * @param locale il locale
   * @throws IllegalArgumentException se il locale o il dominio non rispettano la codifica
   */
  public Indirizzo(final String nome, final String locale, final String dominio)
      throws IllegalArgumentException {
    if (!AddressEncoding.isValidAddressPart(locale))
      throw new IllegalArgumentException("Locale non valido");
    if (!AddressEncoding.isValidAddressPart(dominio))
      throw new IllegalArgumentException("Dominio non valido");
    this.nome = nome;
    this.locale = locale;
    this.dominio = dominio;
  }

  /**
   * Costruttore che inizializza un indirizzo avendo solo locale e dominio
   *
   * @param dominio il dominio
   * @param locale il locale
   * @throws IllegalArgumentException se locale e dominio non rispettano la codifica
   */
  public Indirizzo(final String locale, final String dominio) throws IllegalArgumentException {
    this(null, locale, dominio);
  }

  /**
   * Metodo statico privato che conta gli spazi all'interno di una stringa
   *
   * @param s la stringa in input
   * @return un intero che rappresenta il numero degli spazi
   */
  private static int contaSpazi(String s) {
    int contaSpazi = 0;
    for (char c : s.toCharArray()) {
      if (c == ' ') {
        contaSpazi++;
      }
    }
    return contaSpazi;
  }

  /**
   * Metodo statico che permette di decodificare una stringa
   *
   * <p>Precondizioni: La stringa deve rispettare uno di questi formati:
   *
   * <p>Andrea Rigamonti &lt;andrea.rigamonti@unimi.it&gt;
   *
   * <p>"Andrea Rigamonti Stud" &lt;andrea.rigamonti@unimi.it&gt;
   *
   * <p>andrea.rigamonti@unimi.it
   *
   * @param s la stringa da decodificare
   * @return una lista di stringhe che contiene la stringa decodificata
   * @throws IllegalArgumentException se la stringa contiene caratteri che non sono ASCII
   */
  public static List<String> decodifica(String s) throws IllegalArgumentException {
    if (ASCIICharSequence.isAscii(s)) return AddressEncoding.decode(ASCIICharSequence.of(s)).get(0);
    else throw new IllegalArgumentException("La stringa contiene dei caratteri non ASCII");
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (nome == null) {
      return sb.append(locale).append("@").append(dominio).toString();
    } else {
      if (contaSpazi(nome) <= 1) {
        return sb.append(nome)
            .append(" ")
            .append("<")
            .append(locale)
            .append("@")
            .append(dominio)
            .append(">")
            .toString();
      } else {
        return sb.append('"')
            .append(nome)
            .append('"')
            .append(" ")
            .append("<")
            .append(locale)
            .append("@")
            .append(dominio)
            .append(">")
            .toString();
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Indirizzo altro) {
      return altro.dominio.equals(dominio)
          && altro.locale.equals(locale)
          && altro.nome.equals(nome);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(dominio, locale, nome);
  }
}
