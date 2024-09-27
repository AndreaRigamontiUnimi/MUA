package clients;

import java.util.Scanner;
import mua.Indirizzo;

/** AddressDecode */
public class AddressDecode {

  /**
   * Tests address decoding
   *
   * <p>Reads a line from stdin containing the encoding of an email address and emits three lines in
   * the stout corresponding to the (possibly empty) <em>display name</em>, <em>local</em>, and
   * <em>domain</em> parts of the address.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    Scanner scn = new Scanner(System.in);
    if (scn.hasNextLine()) {
      String riga = scn.nextLine();
      for (String s : Indirizzo.decodifica(riga)) {
        System.out.println(s);
      }
    }
    scn.close();
  }
}
