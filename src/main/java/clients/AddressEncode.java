package clients;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import mua.Indirizzo;

/** AddressEncode */
public class AddressEncode {

  /**
   * Tests address encoding
   *
   * <p>Reads three lines from stdin corresponding to the (possibly empty) <em>display name</em>,
   * <em>local</em>, and <em>domain</em> parts of the address and emits a line in the stout
   * containing the encoding of the email address.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    List<String> elementi = new ArrayList<>();
    try (Scanner scn = new Scanner(System.in)) {
      while (scn.hasNextLine()) {
        elementi.add(scn.nextLine());
      }
    }
    Indirizzo i;
    if (elementi.get(0).isEmpty()) {
      i = new Indirizzo(elementi.get(1), elementi.get(2));
    } else {
      i = new Indirizzo(elementi.get(0), elementi.get(1), elementi.get(2));
    }
    System.out.println(i);
  }
}
