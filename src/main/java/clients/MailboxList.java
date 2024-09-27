package clients;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import mua.MailBox;
import mua.Messaggio;
import utils.Storage;
import utils.UITable;

/** MailboxList */
public class MailboxList {
  /**
   * Tests mailbox listing
   *
   * <p>Runs the app on the commands in the stdin, the commands are limited to: MBOX, LSM, LSE.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      Storage S = new Storage("tests/mbox");
      int index = 1;
      while (sc.hasNextLine()) {
        String riga = sc.nextLine();
        String[] splitted = riga.split(" ");
        String op = riga.split(" ")[0];
        if (splitted.length != 1) {
          index = Integer.parseInt(riga.split(" ")[1]);
        }
        switch (op) {
          case "lsm":
            List<Storage.Box> boxes = S.boxes();
            System.out.println(creaTabella(boxes));
            break;
          case "lse":
            Storage.Box box = S.boxes().get(index - 1);
            List<Storage.Box.Entry> entries = box.entries();
            MailBox m = new MailBox(box.toString());
            for (Storage.Box.Entry entry : entries) {
              m.aggiungiMessaggio(Messaggio.of(entry.content().toString()));
            }
            System.out.println(m.visualizzaElencoDiMessaggi());
            break;
        }
      }
    }
  }

  /**
   * Metodo statico che data una lista di mailboxes, ne stampa la tabella
   *
   * @param boxes una lista di mailboxes
   * @return la tabella
   */
  public static String creaTabella(List<Storage.Box> boxes) {
    List<String> intestazioni = List.of("Mailbox", "# messages");
    List<List<String>> righe = new ArrayList<>();
    for (Storage.Box box : boxes) {
      List<String> riga = new ArrayList<>();
      riga.add(box.toString());
      riga.add(box.entries().size() + "");
      righe.add(riga);
    }
    return UITable.table(intestazioni, righe, true, false);
  }
}
