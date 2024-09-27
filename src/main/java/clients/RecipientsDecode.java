package clients;

import java.util.List;
import java.util.Scanner;
import mua.Destinatario;

/** RecipientsDecode */
public class RecipientsDecode {

  /**
   * Tests recipients decoding
   *
   * <p>Reads a line from stdin containing the encoding of the recipients header and for every
   * address in the header emits a line in stdout containing a comma separated list of three strings
   * corresponding to the (possibly empty) <em>display name</em>, <em>local</em>, and
   * <em>domain</em> parts of the address.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    String riga = "";
    try (Scanner scn = new Scanner(System.in)) {
      if (scn.hasNextLine()) {
        riga = scn.nextLine();
      }
      List<List<String>> l = Destinatario.decodifica(riga);
      for (List<String> ls : l) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ls.size() - 1; i++) {
          sb.append(ls.get(i)).append(", ");
        }
        sb.append(ls.get(ls.size() - 1));
        System.out.println(sb.toString());
      }
    }
  }
}
