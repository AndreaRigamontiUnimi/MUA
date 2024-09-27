package mua;

import clients.MailboxList;
import java.io.IOException;
import java.util.List;
import utils.ASCIICharSequence;
import utils.Storage;
import utils.UIInteract;

/** The application class */
public class App {

  /**
   * Runs the REPL.
   *
   * <p>Develop here the REPL, see the README.md for more details.
   *
   * @param args the first argument is the mailbox base directory.
   */
  public static void main(String[] args) {
    Storage S = new Storage(args[0]);
    try (UIInteract ui = UIInteract.getInstance()) {
      int index = 0;
      boolean change = true;
      MailBox m = new MailBox();
      boolean isEnteredOnce = false;
      for (; ; ) {
        String[] input =
            ui.command("[" + (isEnteredOnce ? S.boxes().get(index).toString() : "*") + "] > ");
        if (change) {
          List<Storage.Box.Entry> entries = S.boxes().get(index).entries();
          m = new MailBox(S.boxes().get(index).toString());
          for (Storage.Box.Entry entry : entries) {
            m.aggiungiMessaggio(Messaggio.of(entry.content().toString()));
          }
        }
        if (input == null) break;
        switch (input[0]) {
          case "LSM":
            ui.output(MailboxList.creaTabella(S.boxes()));
            change = false;
            break;
          case "MBOX":
            index = Integer.parseInt(input[1]) - 1;
            if (index >= S.boxes().size() || index < 0) {
              ui.output("Errore: mailbox non esistente.");
              break;
            }
            isEnteredOnce = true;
            change = true;
            break;
          case "LSE":
            ui.output(m.visualizzaElencoDiMessaggi());
            change = false;
            break;
          case "READ":
            int index2 = Integer.parseInt(input[1]);
            if (!isEnteredOnce) {
              ui.output(
                  "Errore: non hai selezionato nessuna mailbox, esegui prima il comando mbox.");
              break;
            }
            if (index2 > m.size() || index2 < 0) {
              ui.output("Errore: hai selezionato un messaggio inesistente.");
              break;
            }
            ui.output(m.visualizzaMessaggio(index2));
            change = false;
            break;
          case "COMPOSE":
            Messaggio m1 = composeMessaggio(ui);
            m.aggiungiMessaggio(m1);
            S.boxes().get(index).entry(ASCIICharSequence.of(m1.toString()));
            change = false;
            break;
          case "DELETE":
            int index1 = Integer.parseInt(input[1]);
            Messaggio messaggio = m.prendiMessaggio(index1);
            String testo_messaggio = messaggio.toString();
            m.cancellaMessaggio(index1);
            for (Storage.Box.Entry entry : S.boxes().get(index).entries()) {
              if (testo_messaggio.equals(entry.content().toString())) {
                entry.delete();
              }
            }
            change = false;
            break;
          case "EXIT":
            return;
          default:
            ui.error("Unknown command: " + input[0]);
            change = false;
            break;
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Metodo che permette la composizione di un messaggio
   *
   * @param ui la user interface
   * @return un messaggio
   */
  private static Messaggio composeMessaggio(UIInteract ui) {
    Parte pInfo = new ParteInformazioniMessaggio();
    List<String> mit = Mittente.decodifica("From: " + ui.line("From: "));
    List<List<String>> dest = Destinatario.decodifica("To: " + ui.line("To: "));
    Oggetto oggetto = new Oggetto(ui.line("Subject: "));
    Data data = new Data(ui.line("Date: "));
    Mittente mittente =
        new Mittente(mit.get(0).isEmpty() ? null : mit.get(0), mit.get(2), mit.get(1));
    Destinatario destinatario = new Destinatario();
    for (List<String> l : dest) {
      destinatario.aggiungiDestinatario(l.get(0).isEmpty() ? null : l.get(0), l.get(2), l.get(1));
    }
    pInfo.aggiungiIntestazione(mittente);
    pInfo.aggiungiIntestazione(destinatario);
    pInfo.aggiungiIntestazione(oggetto);
    pInfo.aggiungiIntestazione(data);

    ui.output("Text body (. to end):");
    StringBuilder corpoTesto = new StringBuilder();
    String bodyText = ui.line();
    boolean hasTextPart = false;
    Parte pText = new ParteASCII("");
    while (!bodyText.equals(".")) {
      corpoTesto.append(bodyText).append("\n");
      bodyText = ui.line();
    }
    if (!corpoTesto.toString().isEmpty()) {
      hasTextPart = true;
      if (ASCIICharSequence.isAscii(corpoTesto.toString()))
        pText = new ParteASCII(corpoTesto.toString());
      else pText = new ParteNonASCII(corpoTesto.toString());
    }
    ui.output("Text html (. to end):");
    StringBuilder corpoHtml = new StringBuilder();
    String bodyHtml = ui.line();
    boolean hasHtmlPart = false;
    Parte pHtml = new ParteHTML("");
    while (!bodyHtml.equals(".")) {
      corpoHtml.append(bodyHtml).append("\n");
      bodyHtml = ui.line();
    }
    if (!corpoHtml.toString().isEmpty()) {
      hasHtmlPart = true;
      pHtml = new ParteHTML(corpoHtml.toString());
    }

    if (hasHtmlPart && hasTextPart) {
      return new Messaggio(List.of(pInfo, pText, pHtml));
    } else if (hasHtmlPart) {
      return new Messaggio(List.of(pInfo, pHtml));
    } else if (hasTextPart) {
      return new Messaggio(List.of(pInfo, pText));
    } else {
      ui.output("Il messaggio non è valido");
      return null;
    }
  }
}
