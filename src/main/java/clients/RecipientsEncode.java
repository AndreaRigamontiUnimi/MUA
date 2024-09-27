package clients;

import java.util.Scanner;
import mua.Destinatario;

/** RecipientsEncode */
public class RecipientsEncode {

  /**
   * Tests recipients encoding
   *
   * <p>Reads a series of lines from stidn, each containing a comma separated list of three strings
   * corresponding to the (possibly empty) <em>display name</em>, <em>local</em>, and
   * <em>domain</em> parts of an address and emits a line in stdout containing the encoding of the
   * recipients header obtained using such addresses.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    Destinatario d = new Destinatario();
    try (Scanner scn = new Scanner(System.in)) {
      while (scn.hasNextLine()) {
        String riga = scn.nextLine();
        String[] splitted = riga.split(", ");
        d.aggiungiDestinatario(
            splitted[0].isEmpty() ? null : splitted[0], splitted[2], splitted[1]);
      }
    }
    System.out.println(d);
  }
}
