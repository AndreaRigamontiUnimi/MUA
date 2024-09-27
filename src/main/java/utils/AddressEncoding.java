/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** An utility class for decoding and checking email addresses. */
public class AddressEncoding {
  private static final String ADDRESS_PART = "[\\w\\d.!$%&'*+/=?^_`{|}~-]+";

  private static final Predicate<String> ADDRESS_PART_PREDICATE =
      Pattern.compile(ADDRESS_PART).asMatchPredicate();

  private static final Pattern ADDRESS_PATTERN =
      Pattern.compile(
          String.format(
              """
                \\G
                (
                  (
                    (?<name>[-\\w]+(\\s+[-\\w]+)?) |
                    (\"(?<qname>[^\"]*)\")
                  )\\s+
                )?
                (
                  (
                    ((?<local>%1$s)@(?<domain>%1$s)) |
                    (<(?<rlocal>%1$s)@(?<rdomain>%1$s)>)
                  )
                )(\\s*,\\s*)?
              """,
              ADDRESS_PART),
          Pattern.COMMENTS);

  private AddressEncoding() {}

  /**
   * Checks if the given string is a valid email local or domain part.
   *
   * <p>A valid email local or domain part is non {@code null} string that contains only ASCII
   * characters and matches the {@code [\w\d.!$%&'*+/=?^_`{|}~-]+} regular expression.
   *
   * @param part the part.
   * @return if the part is a valid email local or domain part.
   */
  public static boolean isValidAddressPart(final String part) {
    return ASCIICharSequence.isAscii(part) && ADDRESS_PART_PREDICATE.test(part);
  }

  /**
   * Decodes a {@link ASCIICharSequence} into a list of email addresses parts.
   *
   * <p>Every element of the list is a list of three non {@code null} strings corresponding
   * respectively to the <em>display name</em>, <em>local</em>, and <em>domain</em> parts of an
   * email address.
   *
   * <p>The {@link ASCIICharSequence} should contain a sequence of email addresses separated by
   * commas; every address is given by the display name (enclosed in quotes, if composed by more
   * than two words) followed by the local part and the domain part separated by the {@code @} sign.
   * Examples of encoded email addresses are:
   *
   * <ul>
   *   <li>{@code rossi@libero.com}
   *   <li>{@code "Mario Carlo Rossi" <mcr@gmail.com>}
   *   <li>{@code Ronaldo <ronaldo.callegari@foo.it>}
   *   <li>{@code Piero Carli <pc@mac.mec.it>}
   * </ul>
   *
   * the result of this method applied to the above sequences is the list of the following lists:
   *
   * <ul>
   *   <li>{@code "", "rossi", "libero.com"}
   *   <li>{@code "Mario Carlo Rossi", "mcr", "gmail.com"}
   *   <li>{@code "Ronaldo", "ronaldo.callegari", "foo.it"}
   *   <li>{@code "Piero Carli", "pc", "mac.mec.it"}
   * </ul>
   *
   * In case the comma separated sequence contains invalid address, such address will not appear in
   * the returned list; in case all addresses are invalid, the returned list will be empty.
   *
   * @param sequence the sequence encoding the email addresses.
   * @return the list of email addresses parts.
   * @throws NullPointerException if the given sequence is {@code null}.
   */
  public static List<List<String>> decode(ASCIICharSequence sequence) throws NullPointerException {
    final List<List<String>> result = new ArrayList<>();
    final Matcher m = ADDRESS_PATTERN.matcher(Objects.requireNonNull(sequence));
    while (m.find()) {
      final String name;
      if (m.group("name") != null) name = m.group("name");
      else if (m.group("qname") != null) name = m.group("qname");
      else name = "";
      if (m.group("local") != null) result.add(List.of(name, m.group("local"), m.group("domain")));
      else result.add(List.of(name, m.group("rlocal"), m.group("rdomain")));
    }
    return List.copyOf(result);
  }
}
