package clients;

import java.util.Scanner;
import mua.Oggetto;

/** SubjectEncode */
public class SubjectEncode {

  /**
   * Tests subject value encoding
   *
   * <p>Reads a line from stdin containing the value of a subject header and emits its encoded
   * version in the stdout.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    try (Scanner scn = new Scanner(System.in)) {
      if (scn.hasNextLine()) {
        String riga = scn.nextLine();
        Oggetto obj = new Oggetto(riga);
        System.out.println(obj.codifica());
      }
    }
  }
}
