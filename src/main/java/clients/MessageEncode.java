package clients;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;
import mua.*;
import utils.ASCIICharSequence;
import utils.DateEncoding;

/** MessageEncode */
public class MessageEncode {

  public static final ZonedDateTime DATE =
      ZonedDateTime.of(2023, 12, 6, 12, 30, 20, 200, ZoneId.of("Europe/Rome"));

  /**
   * Tests message encoding
   *
   * <p>Reads a message from stdin and emits its encoding on the stdout.
   *
   * <p>The stdin contains:
   *
   * <ul>
   *   <li>the sender address (three lines, see {@link AddressDecode}),
   *   <li>two recipient addresses (three lines each, as above),
   *   <li>the subject (one line),
   *   <li>the text part (one line, possibly empty),
   *   <li>the HTML part (one line, possibly empty).
   * </ul>
   *
   * To such information, the program adds the date corresponding to {@link #DATE}.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    Messaggio m;
    try (Scanner scn = new Scanner(System.in)) {
      String nome_mittente = scn.nextLine();
      String locale_mittente = scn.nextLine();
      String dominio_mittente = scn.nextLine();
      String nome_destinatario1 = scn.nextLine();
      String locale_destinatario1 = scn.nextLine();
      String dominio_destinatario1 = scn.nextLine();
      String nome_destinatario2 = scn.nextLine();
      String locale_destinatario2 = scn.nextLine();
      String dominio_destinatario2 = scn.nextLine();
      String oggetto = scn.nextLine();
      String testo = scn.nextLine();
      String html = scn.nextLine();
      Mittente mitt = new Mittente(nome_mittente, dominio_mittente, locale_mittente);
      Data data = new Data(DateEncoding.encode(DATE).toString());
      Oggetto obj = new Oggetto(oggetto);
      Destinatario destinatario = new Destinatario();
      destinatario.aggiungiDestinatario(
          nome_destinatario1.isEmpty() ? null : nome_destinatario1,
          dominio_destinatario1,
          locale_destinatario1);
      destinatario.aggiungiDestinatario(
          nome_destinatario2.isEmpty() ? null : nome_destinatario2,
          dominio_destinatario2,
          locale_destinatario2);
      Parte p_info = new ParteInformazioniMessaggio();
      p_info.aggiungiIntestazione(mitt);
      p_info.aggiungiIntestazione(destinatario);
      p_info.aggiungiIntestazione(obj);
      p_info.aggiungiIntestazione(data);
      if (!testo.isEmpty() && !html.isEmpty()) {
        m =
            new Messaggio(
                List.of(
                    p_info,
                    (ASCIICharSequence.isAscii(testo)
                        ? new ParteASCII(testo)
                        : new ParteNonASCII(testo)),
                    new ParteHTML(html)));
      } else if (html.isEmpty()) {
        m =
            new Messaggio(
                List.of(
                    p_info,
                    (ASCIICharSequence.isAscii(testo)
                        ? new ParteASCII(testo)
                        : new ParteNonASCII(testo))));
      } else {
        m = new Messaggio(List.of(p_info, new ParteHTML(html)));
      }
    }
    System.out.println(m);
  }
}
