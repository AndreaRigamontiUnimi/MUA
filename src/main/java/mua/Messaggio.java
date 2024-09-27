package mua;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.*;

/** Classe mutabile che definisce un messaggio */
public class Messaggio implements Comparable<Messaggio> {
  /** Le parti del messaggio */
  private final List<Parte> parti = new ArrayList<>();

  /*
   *
   * AF:
   *   i campi del messaggio servono per la visualizzazione corretta e completa di
   *   un messaggio (di una mail)
   *   parti(0) è una parte contenente le informazioni
   *   parti(1) è una parte ascii o non ascii se ci sono
   *   parti(2) è una parte html se c'è
   * RI:
   *   tutti i campi di un messaggio non devono essere null
   *   parti(0) non può essere null e anche almeno una delle due parti (1,2) non deve essere null
   * */

  /**
   * Costruttore di un messaggio
   *
   * @param parti una lista di parti
   * @throws NullPointerException se la lista delle parti è null
   * @throws IllegalArgumentException se la lista delle parti non contiene abbastanza parti
   */
  public Messaggio(List<Parte> parti) throws NullPointerException, IllegalArgumentException {
    if (parti.size() < 2)
      throw new IllegalArgumentException("Il messaggio non può contenere meno di due parti");
    this.parti.addAll(Objects.requireNonNull(parti));
    aggiungiIntestazioniMancanti();
  }

  /** Metodo che aggiunge tutte le intestazioni mancanti alle parti */
  private void aggiungiIntestazioniMancanti() {
    if (parti.size() > 2) {
      Parte totale = parti.get(0);
      totale.aggiungiIntestazione(Intestazione.INTESTAZIONE_MIME);
      totale.aggiungiIntestazione(Intestazione.INTESTAZIONE_MULTIPARTE);
      if (parti.get(1).getClass() == ParteASCII.class) {
        Parte ascii = parti.get(1);
        ascii.aggiungiIntestazione(Intestazione.ASCII_INTESTAZIONE);
      } else {
        Parte non_ascii = parti.get(1);
        non_ascii.aggiungiIntestazione(Intestazione.NON_ASCII_INTESTAZIONE);
        non_ascii.aggiungiIntestazione(Intestazione.INTESTAZIONE_TRANSFER_ENCODING);
      }
      Parte html = parti.get(2);
      html.aggiungiIntestazione(Intestazione.HTML_INTESTAZIONE);
      html.aggiungiIntestazione(Intestazione.INTESTAZIONE_TRANSFER_ENCODING);
    } else {
      if (parti.get(1).getClass() == ParteHTML.class) {
        Parte html = parti.get(1);
        html.aggiungiIntestazione(Intestazione.HTML_INTESTAZIONE);
        html.aggiungiIntestazione(Intestazione.INTESTAZIONE_TRANSFER_ENCODING);
      } else {
        if (parti.get(1).getClass() == ParteASCII.class) {
          Parte ascii = parti.get(1);
          ascii.aggiungiIntestazione(Intestazione.ASCII_INTESTAZIONE);
        } else {
          Parte non_ascii = parti.get(1);
          non_ascii.aggiungiIntestazione(Intestazione.NON_ASCII_INTESTAZIONE);
          non_ascii.aggiungiIntestazione(Intestazione.INTESTAZIONE_TRANSFER_ENCODING);
        }
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (parti.size() > 2) {
      Parte totale = parti.get(0);
      sb.append(totale)
          .append("\nThis is a message with multiple parts in MIME format.")
          .append("\n--frontier\n");
      if (parti.get(1).getClass() == ParteASCII.class) {
        Parte ascii = parti.get(1);
        sb.append(ascii);
      } else {
        Parte non_ascii = parti.get(1);
        sb.append(non_ascii);
      }
      sb.append("\n--frontier\n");
      Parte html = parti.get(2);
      sb.append(html).append("\n--frontier--\n");
    } else {
      sb.append(parti.get(0));
      if (parti.get(1).getClass() == ParteHTML.class) {
        Parte html = parti.get(1);
        sb.append(html);
      } else {
        if (parti.get(1).getClass() == ParteASCII.class) {
          Parte ascii = parti.get(1);
          sb.append(ascii);
        } else {
          Parte non_ascii = parti.get(1);
          sb.append(non_ascii);
        }
      }
    }
    return sb.toString();
  }

  @Override
  public int compareTo(Messaggio o) {
    Intestazione i = this.parti.get(0).intestazioni().get(3);
    Intestazione i2 = o.parti.get(0).intestazioni().get(3);
    if (i.getClass() == Data.class && i2.getClass() == Data.class) {
      Data d = (Data) i;
      Data d2 = (Data) i2;
      return d.compareTo(d2);
    }
    throw new IllegalArgumentException(
        "Le intestazioni di questa parte non sono in ordine corretto");
  }

  /**
   * Metodo statico che decodifica un messaggio in questo modo:
   *
   * <pre>
   *  +-----------------+---------------+
   *  | An header       | A value       |
   *  |                 | on            |
   *  |                 | three lines   |
   *  +-----------------+---------------+
   *  | A multiline     |               |
   *  | header          |               |
   *  +-----------------+---------------+
   *  | Another header  | Another value |
   *  +-----------------+---------------+
   * </pre>
   *
   * @param messaggio una stringa che definisce il messaggio da decodificare
   * @return la decodifica formattata secondo una {@link utils.UICard}
   * @throws NullPointerException se messaggio è null
   */
  public static String decodifica(final String messaggio) throws NullPointerException {
    Objects.requireNonNull(messaggio);
    ASCIICharSequence sequence = ASCIICharSequence.of(messaggio);

    List<String> headers = new ArrayList<>();
    List<String> values = new ArrayList<>();

    List<Fragment> fragments = EntryEncoding.decode(sequence);

    Fragment f = fragments.get(0);
    for (List<ASCIICharSequence> header : f.rawHeaders()) {
      String tipo = header.get(0).toString();
      if (tipo.equals("from")
          || tipo.equals("to")
          || tipo.equals("subject")
          || tipo.equals("date")) {
        headers.add(primaMaiuscola(tipo));
        switch (tipo) {
          case "to" -> {
            StringBuilder valore = new StringBuilder();
            String[] destinatari = header.get(1).toString().split(", ");
            for (String s : destinatari) {
              valore.append(s).append("\n");
            }
            values.add(valore.toString());
          }
          case "subject" -> values.add(
              header.get(1).toString().startsWith("=?utf-8?B?")
                      && header.get(1).toString().endsWith("?=")
                  ? Base64Encoding.decodeWord(ASCIICharSequence.of(header.get(1).toString()))
                  : header.get(1).toString());
          case "date" -> {
            ZonedDateTime zdt = DateEncoding.decode(ASCIICharSequence.of(header.get(1).toString()));
            values.add(
                zdt.getYear()
                    + "-"
                    + (zdt.getMonthValue() < 10 ? "0" + zdt.getMonthValue() : zdt.getMonthValue())
                    + "-"
                    + (zdt.getDayOfMonth() < 10 ? "0" + zdt.getDayOfMonth() : zdt.getDayOfMonth())
                    + "T"
                    + (zdt.getHour() < 10 ? "0" + zdt.getHour() : zdt.getHour())
                    + ":"
                    + (zdt.getMinute() < 10 ? "0" + zdt.getMinute() : zdt.getMinute())
                    + ":"
                    + (zdt.getSecond() < 10 ? "0" + zdt.getSecond() : zdt.getSecond())
                    + zdt.getOffset());
          }
          default -> values.add(header.get(1).toString());
        }
      } else if (tipo.equals("content-type")) {
        switch (header.get(1).toString()) {
          case "text/plain; charset=\"us-ascii\"" -> {
            headers.add("Part\ntext/plain");
            values.add(f.rawBody().toString());
          }
          case "text/plain; charset=\"utf-8\"" -> {
            headers.add("Part\ntext/plain");
            values.add(Base64Encoding.decode(ASCIICharSequence.of(f.rawBody().toString())));
          }
          case "text/html; charset=\"utf-8\"" -> {
            headers.add("Part\ntext/html");
            values.add(Base64Encoding.decode(ASCIICharSequence.of(f.rawBody().toString())));
          }
          case "multipart/alternative; boundary=frontier" -> {
            headers.add("Part\nmultipart/alternative");
            values.add(f.rawBody().toString());
          }
        }
      }
    }
    if (fragments.size() > 1) {
      for (int i = 1; i < fragments.size(); i++) {
        Fragment fragment = fragments.get(i);
        for (List<ASCIICharSequence> header : fragment.rawHeaders()) {
          String tipo = header.get(0).toString();
          if (tipo.equals("content-type")) {
            switch (header.get(1).toString()) {
              case "text/plain; charset=\"us-ascii\"" -> {
                headers.add("Part\ntext/plain");
                values.add(fragment.rawBody().toString());
              }
              case "text/plain; charset=\"utf-8\"" -> {
                headers.add("Part\ntext/plain");
                values.add(
                    Base64Encoding.decode(ASCIICharSequence.of(fragment.rawBody().toString())));
              }
              case "text/html; charset=\"utf-8\"" -> {
                headers.add("Part\ntext/html");
                values.add(
                    Base64Encoding.decode(ASCIICharSequence.of(fragment.rawBody().toString())));
              }
            }
          }
        }
      }
    }
    return UICard.card(headers, values);
  }

  /**
   * Metodo statico che permette di scrivere una parola la cui prima lettera è minuscola, con la
   * prima lettera maiuscola
   *
   * @param s la stringa da mutare
   * @return la stringa con la prima lettera maiuscola
   */
  private static String primaMaiuscola(String s) {
    return Character.toString(s.charAt(0) - 32) + s.substring(1);
  }

  /**
   * Partendo dalla codifica di un messaggio, restituisce l'oggetto relativo
   *
   * @param messaggio la codifica di un messaggio
   * @return un oggetto di tipo Messaggio relativo al messaggio codificato in input
   * @throws NullPointerException se messaggio è null
   */
  public static Messaggio of(final String messaggio) {
    Objects.requireNonNull(messaggio);
    ASCIICharSequence sequence = ASCIICharSequence.of(messaggio);

    List<Fragment> fragments = EntryEncoding.decode(sequence);

    Parte info = new ParteInformazioniMessaggio();

    Fragment f = fragments.get(0);
    for (List<ASCIICharSequence> header : f.rawHeaders()) {
      String tipo = header.get(0).toString();
      if (tipo.equals("from")
          || tipo.equals("to")
          || tipo.equals("subject")
          || tipo.equals("date")) {
        switch (tipo) {
          case "to" -> {
            Destinatario d = new Destinatario();
            List<List<String>> d_decode =
                Destinatario.decodifica("To: " + header.get(1).toString());
            for (List<String> destinatario : d_decode) {
              d.aggiungiDestinatario(
                  destinatario.get(0).isEmpty() ? null : destinatario.get(0),
                  destinatario.get(1),
                  destinatario.get(2));
            }
            info.aggiungiIntestazione(d);
          }
          case "subject" -> info.aggiungiIntestazione(
              new Oggetto(Oggetto.decodifica(header.get(1).toString())));
          case "date" -> info.aggiungiIntestazione(new Data(header.get(1).toString()));
          default -> {
            List<String> mittente = Mittente.decodifica("From: " + header.get(1).toString());
            info.aggiungiIntestazione(
                new Mittente(
                    mittente.get(0).isEmpty() ? null : mittente.get(0),
                    mittente.get(2),
                    mittente.get(1)));
          }
        }
      } else if (tipo.equals("content-type")) {
        switch (header.get(1).toString()) {
          case "text/plain; charset=\"us-ascii\"" -> {
            Parte p1 = new ParteASCII(f.rawBody().toString());
            return new Messaggio(List.of(info, p1));
          }
          case "text/plain; charset=\"utf-8\"" -> {
            Parte p1 =
                new ParteNonASCII(
                    Base64Encoding.decode(ASCIICharSequence.of(f.rawBody().toString())));
            return new Messaggio(List.of(info, p1));
          }
          case "text/html; charset=\"utf-8\"" -> {
            Parte p1 =
                new ParteHTML(Base64Encoding.decode(ASCIICharSequence.of(f.rawBody().toString())));
            return new Messaggio(List.of(info, p1));
          }
        }
      }
    }
    Parte pASCII = new ParteASCII("");
    Parte pNonASCII = new ParteNonASCII("");
    Parte pHTML = new ParteHTML("");
    boolean isAsciiPart = false;
    if (fragments.size() > 1) {
      for (int i = 1; i < fragments.size(); i++) {
        Fragment fragment = fragments.get(i);
        for (List<ASCIICharSequence> header : fragment.rawHeaders()) {
          String tipo = header.get(0).toString();
          if (tipo.equals("content-type")) {
            switch (header.get(1).toString()) {
              case "text/plain; charset=\"us-ascii\"" -> {
                isAsciiPart = true;
                pASCII = new ParteASCII(fragment.rawBody().toString());
              }
              case "text/plain; charset=\"utf-8\"" -> {
                pNonASCII =
                    new ParteNonASCII(
                        Base64Encoding.decode(ASCIICharSequence.of(fragment.rawBody().toString())));
              }
              case "text/html; charset=\"utf-8\"" -> {
                pHTML =
                    new ParteHTML(
                        Base64Encoding.decode(ASCIICharSequence.of(fragment.rawBody().toString())));
              }
            }
          }
        }
      }
    }
    return new Messaggio(List.of(info, isAsciiPart ? pASCII : pNonASCII, pHTML));
  }

  /**
   * Metodo che permette l'osservazione del mittente di questo messaggio
   *
   * @return il mittente di questo messaggio
   * @throws RuntimeException se l'intestazione cercata non è quella di un mittente
   */
  public String mittente() {
    if (parti.get(0).intestazioni().get(0).getClass() == Mittente.class) {
      Mittente m = (Mittente) parti.get(0).intestazioni().get(0);
      List<String> mitt = Mittente.decodifica(m.toString());
      return mitt.get(1) + "@" + mitt.get(2);
    }
    throw new RuntimeException("L'intestazione selezionata non è un mittente");
  }

  /**
   * metodo che ci permette di osservare l'elenco dei destinatari di questo messaggio
   *
   * <p>indirizzo destinatario1
   *
   * <p>...
   *
   * <p>indirizzo destinatarion
   *
   * @return l'elenco dei destinatari di questo messaggio
   * @throws RuntimeException se l'intestazione cercata non è quella di un destinatario
   */
  public String destinatario() {
    if (parti.get(0).intestazioni().get(1).getClass() == Destinatario.class) {
      Destinatario destinatario = (Destinatario) parti.get(0).intestazioni().get(1);
      return destinatario.formattaDestinatario();
    }
    throw new RuntimeException("L'intestazione selezionata non è un destinatario");
  }

  /**
   * Osserva l'oggetto di questo messaggio
   *
   * @return l'oggetto di questo messaggio
   * @throws RuntimeException se l'intestazione cercata non è quella di un oggetto
   */
  public String oggetto() {
    if (parti.get(0).intestazioni().get(2).getClass() == Oggetto.class) {
      Oggetto obj = (Oggetto) parti.get(0).intestazioni().get(2);
      return Oggetto.decodifica(obj.valore());
    }
    throw new RuntimeException("L'intestazione selezionata non è un oggetto");
  }

  /**
   * metodo che ci permette di osservare la data formattata di questo messaggio
   *
   * <p>anno-mese-giorno
   *
   * <p>ore:minuti:secondi
   *
   * @return la data formattata di questo messaggio
   * @throws RuntimeException se l'intestazione cercata non è quella di una data
   */
  public String data() {
    if (parti.get(0).intestazioni().get(3).getClass() == Data.class) {
      Data data = (Data) parti.get(0).intestazioni().get(3);
      return data.formattaData();
    }
    throw new RuntimeException("L'intestazione selezionata non è una data");
  }
}
