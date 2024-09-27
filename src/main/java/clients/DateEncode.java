package clients;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;
import utils.DateEncoding;

/** DateEncode */
public class DateEncode {

  /**
   * Tests date encoding
   *
   * <p>Reads three integers from stdin corresponding to an year, month and day and emits a line in
   * the stout containing the encoding of the date corresponding to the exact midnight of such date
   * (in the "Europe/Rome" timezone).
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      if (scanner.hasNextLine()) {
        String riga = scanner.nextLine();
        String[] splitted = riga.split(" ");
        int year = Integer.parseInt(splitted[0]);
        int month = Integer.parseInt(splitted[1]);
        int day = Integer.parseInt(splitted[2]);
        ZonedDateTime z = ZonedDateTime.of(year, month, day, 0, 0, 0, 0, ZoneId.of("Europe/Rome"));
        System.out.println(DateEncoding.encode(z));
      }
    }
  }
}
