/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/** An utility class to encode and decode a date. */
public class DateEncoding {

  private DateEncoding() {}

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

  /** The {@link ZoneId} of Europe/Rome. */
  public static final ZoneId EUROPE_ROME = ZoneId.of("Europe/Rome");

  /**
   * Encodes a {@link ZonedDateTime} date to an {@link ASCIICharSequence}.
   *
   * @param date the date.
   * @return the encoded sequence.
   * @throws DateTimeException if the date cannot be formatted.
   * @throws NullPointerException if the date is {@code null}.
   */
  public static ASCIICharSequence encode(final ZonedDateTime date)
      throws DateTimeException, NullPointerException {
    return ASCIICharSequence.of(Objects.requireNonNull(date).format(DATE_FORMATTER));
  }

  /**
   * Decodes the {@link ASCIICharSequence} to a {@link ZonedDateTime}.
   *
   * @param sequence the sequence.
   * @return the date.
   * @throws DateTimeParseException if the sequence cannot be parsed.
   * @throws NullPointerException if the sequence is {@code null}.
   */
  public static ZonedDateTime decode(final ASCIICharSequence sequence) {
    return ZonedDateTime.parse(Objects.requireNonNull(sequence), DATE_FORMATTER);
  }
}
