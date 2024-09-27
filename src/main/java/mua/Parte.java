package mua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Classe astratta che descrive una parte generica di un messaggio */
public abstract class Parte {
  /** La lista delle intestazioni di una parte di messaggio */
  protected final List<Intestazione> intestazioni = new ArrayList<>();

  /** Il corpo di un messaggio */
  protected final Corpo corpo;

  /*
   * AF:
   *     Headers:
   *       intestazioni[i] per 0 < i < n
   *     Body:
   *       corpo
   * RI:
   *   intestazioni non deve essere null
   *   corpo non deve essere null
   *   intestazioni non deve contenere null
   * */

  /**
   * Costruisce una parte senza intestazioni
   *
   * @param c il corpo di questa parte
   * @throws NullPointerException se il corpo passato al costruttore è null
   */
  protected Parte(final Corpo c) throws NullPointerException {
    corpo = Objects.requireNonNull(c);
  }

  /**
   * Aggiunge una intestazione alle intestazioni di una parte
   *
   * @param i l'intestazione da aggiungere
   * @throws NullPointerException se l'intestazione è null
   */
  public void aggiungiIntestazione(final Intestazione i) throws NullPointerException {
    intestazioni.add(Objects.requireNonNull(i));
  }

  /**
   * Visualizza il corpo, decodificato, del messaggio
   *
   * @return il testo del corpo decodificato
   */
  public String visualizzaCorpoDecodificato() {
    return corpo.decodifica();
  }

  /**
   * Metodo osservazionale che mi restituisce la lista delle intestazioni ontenute in una parte
   *
   * @return una copia della lista delle intestazioni
   */
  public List<Intestazione> intestazioni() {
    return Collections.unmodifiableList(intestazioni);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Intestazione i : intestazioni) {
      sb.append(i.tipo()).append(": ").append(i.valore()).append("\n");
    }
    if (!corpo.codifica().isEmpty()) return sb.append("\n").append(corpo.codifica()).toString();
    else return sb.toString();
  }
}
