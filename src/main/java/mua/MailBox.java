package mua;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.UITable;

/** Classe immutabile corrispondente a una collezione di {@link Messaggio} */
public class MailBox {
  /** Nome della mailbox */
  private final String nome;

  /** Insieme di messaggi contenuti in una mailbox */
  private final List<Messaggio> messaggi = new ArrayList<>();

  /*
   * AF:
   *   Nome -> messaggi[0], messaggi[1],..., messaggi[n]
   * RI:
   *   messaggi non deve essere null
   *   messaggi[i] non deve essere null
   * */

  /**
   * Costruttore di una mailbox
   *
   * @param nome il nome della mailbox
   * @throws NullPointerException se il nome è null
   */
  public MailBox(final String nome) throws NullPointerException {
    this.nome = Objects.requireNonNull(nome);
  }

  /** Costruttore di default, che assegna in automatico alla mailbox il nome "INBOX" */
  public MailBox() {
    this("INBOX");
  }

  /**
   * Metodo che permette di aggiungere ai messaggi della mailbox un nuovo messaggio
   *
   * @param m il messaggio da aggiungere
   * @throws NullPointerException se il messaggio è null
   */
  public void aggiungiMessaggio(Messaggio m) throws NullPointerException {
    Objects.requireNonNull(m);
    if (messaggi.isEmpty()) {
      messaggi.add(m);
      return;
    }

    if (messaggi.size() == 1) {
      if (m.compareTo(messaggi.get(0)) > 0) {
        messaggi.add(0, m);
      } else {
        messaggi.add(m);
      }
      return;
    }
    int pos_f = messaggi.size();
    for (int i = 0; i < messaggi.size(); i++) {
      if (m.compareTo(messaggi.get(i)) > 0) {
        pos_f = i;
        break;
      }
    }
    messaggi.add(pos_f, m);
  }

  /**
   * Metodo che permette la cancellazione del messaggio<br>
   * REQUIRES: i > 0, si suppone che l'utente legga i messaggi con indici da 1 a n, dove n è la
   * lunghezza di messaggi
   *
   * @param i indice del messaggio da eliminare
   * @throws IndexOutOfBoundsException se l'indice inserito non è nell'insieme {1,n}
   */
  public void cancellaMessaggio(int i) throws IndexOutOfBoundsException {
    messaggi.remove(i - 1);
  }

  /**
   * Metodo che dato un indice(diminuito di 1) mi permette di trovare il messaggio associato
   *
   * @param i l'indice del messaggio
   * @return il messaggio associato a i - 1
   * @throws IndexOutOfBoundsException se non è presente all'indice i-1 un messaggio
   */
  public Messaggio prendiMessaggio(int i) throws IndexOutOfBoundsException {
    return messaggi.get(i - 1);
  }

  /**
   * Metodo che permette la visualizzazione di un messaggio<br>
   * REQUIRES: i > 0, si suppone che l'utente legga i messaggi con indici da 1 a n
   *
   * @param i indice del messaggio da visualizzare
   * @return la visualizzazione del messaggio in posizione i
   * @throws IndexOutOfBoundsException se l'indice inserito non è nell'insieme {1,n}
   */
  public String visualizzaMessaggio(int i) throws IndexOutOfBoundsException {
    return Messaggio.decodifica(messaggi.get(i - 1).toString());
  }

  /**
   * Elenca, secondo il formato seguente, i messaggi dentro la mailbox <br>
   *
   * <pre>
   * +===+===============+===============+==============+
   * | # | First header  | Second header | Third header |
   * +===+===============+===============+==============+
   * | 1 | A multi       |               | A value      |
   * |   | line value    |               | on           |
   * |   |               |               | three lines  |
   * +---+---------------+---------------+--------------+
   * | 2 | Another value | On            | A value      |
   * |   |               | two lines     |              |
   * +===+===============+===============+==============+
   * </pre>
   *
   * @return la stringa che contiene l'elenco dei messaggi
   */
  public String visualizzaElencoDiMessaggi() {
    List<List<String>> rows = new ArrayList<>();
    for (Messaggio m : messaggi) {
      rows.add(List.of(m.data(), m.mittente(), m.destinatario(), m.oggetto()));
    }
    return UITable.table(List.of("Date", "From", "To", "Subject"), rows, true, true);
  }

  /**
   * Metodo che restituisce il nome della mailbox
   *
   * @return il nome della mailbox
   */
  public String nome() {
    return nome;
  }

  /**
   * Metodo che restituisce la dimensione della mailbox
   *
   * @return il numero di messaggi contenuti
   */
  public int size() {
    return messaggi.size();
  }
}
