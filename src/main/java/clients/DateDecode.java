package clients;

import java.util.Scanner;
import utils.ASCIICharSequence;
import utils.DateEncoding;

/** DateDecode */
public class DateDecode {

  /**
   * Tests date decoding
   *
   * <p>Reads a line from stdin containing the encoding of a date and emits the corresponding day of
   * week in the stout.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    try (Scanner scn = new Scanner(System.in)) {
      if (scn.hasNextLine()) {
        String riga = scn.nextLine();
        System.out.println(
            DateEncoding.decode(ASCIICharSequence.of(riga)).getDayOfWeek().toString());
      }
    }
  }
}
