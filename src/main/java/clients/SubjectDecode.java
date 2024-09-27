package clients;

import java.util.Scanner;
import mua.Oggetto;

/** SubjectDecode */
public class SubjectDecode {

  /**
   * Tests subject value decoding
   *
   * <p>Reads a line from stdin containing the encoding of the value of a subject header and emits
   * its decoded version in the stdout.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    try (Scanner scn = new Scanner(System.in)) {
      if (scn.hasNextLine()) {
        String riga = scn.nextLine();
        System.out.println(Oggetto.decodifica(riga));
      }
    }
  }
}
