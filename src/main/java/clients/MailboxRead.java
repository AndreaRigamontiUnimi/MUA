package clients;

import java.util.Scanner;
import mua.Messaggio;
import utils.Storage;

/** MailboxRead */
public class MailboxRead {
  /**
   * Tests message reading
   *
   * <p>Runs the app on the commands in the stdin, the commands are limited to: MBOX, READ.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      Storage s = new Storage("tests/mbox");
      while (sc.hasNextLine()) {
        int box_index = Integer.parseInt(sc.nextLine().split(" ")[1]) - 1;
        int mex_index = Integer.parseInt(sc.nextLine().split(" ")[1]) - 1;
        Storage.Box b = s.boxes().get(box_index);
        Storage.Box.Entry entry = b.entries().get(mex_index);
        System.out.println(Messaggio.decodifica(entry.content().toString()));
      }
    }
  }
}
