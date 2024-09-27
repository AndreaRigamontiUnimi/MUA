package clients;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import mua.*;
import utils.ASCIICharSequence;
import utils.Storage;

/** MailboxCompose */
public class MailboxCompose {

  /**
   * Tests message composition and deletion
   *
   * <p>Runs the app on the commands in the stdin, the commands are limited to: MBOX, COMPOSE, READ,
   * DELTE.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      Storage s = new Storage("tests/mbox");
      List<String> list = new ArrayList<>();
      while (sc.hasNextLine()) {
        list.add(sc.nextLine());
      }

      int indicePunto1 = 0;
      int indicePunto2 = 0;
      boolean primo_punto = true;

      for (int i = 0; i < list.size(); i++) {
        if (list.get(i).equals(".")) {
          if (primo_punto) {
            primo_punto = false;
            indicePunto1 = i;
          } else {
            indicePunto2 = i;
          }
        }
      }
      int index = Integer.parseInt(list.get(0).split(" ")[1]) - 1;
      Storage.Box box = s.boxes().get(index);
      MailBox m = new MailBox(box.toString());
      for (Storage.Box.Entry entry : box.entries()) {
        m.aggiungiMessaggio(Messaggio.of(entry.content().toString()));
      }
      for (int i = 1; i < list.size(); i++) {
        String st = list.get(i);
        if (st.equals("compose")) {
          Parte p_info = new ParteInformazioniMessaggio();
          Parte pAscii = new ParteASCII("");
          Parte pNonAscii = new ParteNonASCII("");
          Parte pHtml;
          List<String> mit = Mittente.decodifica("From: " + list.get(2));
          List<List<String>> dest = Destinatario.decodifica("To: " + list.get(3));
          Oggetto ogg = new Oggetto(list.get(4));
          Data data = new Data(list.get(5));
          Mittente mittente =
              new Mittente(mit.get(0).isEmpty() ? null : mit.get(0), mit.get(2), mit.get(1));
          Destinatario destinatario = new Destinatario();
          for (List<String> l : dest) {
            destinatario.aggiungiDestinatario(
                l.get(0).isEmpty() ? null : l.get(0), l.get(2), l.get(1));
          }
          p_info.aggiungiIntestazione(mittente);
          p_info.aggiungiIntestazione(destinatario);
          p_info.aggiungiIntestazione(ogg);
          p_info.aggiungiIntestazione(data);
          i = 6;
          String corpo_testuale = "";
          boolean hasTextBody = true;
          while (i < indicePunto1) {
            corpo_testuale += list.get(i) + "\n";
            i++;
          }
          i++;
          if (corpo_testuale.isEmpty()) hasTextBody = false;
          if (ASCIICharSequence.isAscii(corpo_testuale)) {
            pAscii = new ParteASCII(corpo_testuale);
          } else {
            pNonAscii = new ParteNonASCII(corpo_testuale);
          }
          String corpo_html = "";
          boolean hasHtml = true;
          while (i < indicePunto2) {
            corpo_html += list.get(i) + "\n";
            i++;
          }
          if (corpo_html.isEmpty()) hasHtml = false;
          pHtml = new ParteHTML(corpo_html);
          if (hasHtml && hasTextBody) {
            m.aggiungiMessaggio(
                new Messaggio(
                    List.of(
                        p_info,
                        ASCIICharSequence.isAscii(corpo_testuale) ? pAscii : pNonAscii,
                        pHtml)));
          } else if (hasHtml) {
            m.aggiungiMessaggio(new Messaggio(List.of(p_info, pHtml)));
          } else if (hasTextBody) {
            Messaggio m1 =
                new Messaggio(
                    List.of(
                        p_info, ASCIICharSequence.isAscii(corpo_testuale) ? pAscii : pNonAscii));
            m.aggiungiMessaggio(m1);
          } else {
            System.out.println("Messaggio non valido!");
          }
        } else if (st.startsWith("read")) {
          int indice_messaggio = Integer.parseInt(st.split(" ")[1]);
          System.out.println(m.visualizzaMessaggio(indice_messaggio));
        } else if (st.startsWith("delete")) {
          int indice_messaggio = Integer.parseInt(st.split(" ")[1]);
          m.cancellaMessaggio(indice_messaggio);
        }
      }
    }
  }
}
