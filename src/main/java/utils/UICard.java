/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * An utility class to build a <em>card</em>.
 *
 * <h2>Example</h2>
 *
 * The code
 *
 * <pre>
 * card(
 *     List.of("An header", "A multiline\nheader", "Another header"),
 *     List.of("A value\non\nthree lines", "", "Another value"))
 * )
 * </pre>
 *
 * returns the following string
 *
 * <pre>
 * +-----------------+---------------+
 * | An header       | A value       |
 * |                 | on            |
 * |                 | three lines   |
 * +-----------------+---------------+
 * | A multiline     |               |
 * | header          |               |
 * +-----------------+---------------+
 * | Another header  | Another value |
 * +-----------------+---------------+
 * </pre>
 */
public class UICard {

  private UICard() {}

  private static int maxWidth(List<String> data) {
    int maxWidth = 0, m;
    for (String e : data)
      for (String l : e.split("\n")) if ((m = l.length()) > maxWidth) maxWidth = m;
    return maxWidth;
  }

  private static void hl(
      final int maxHeaderWidth, final int maxValuesWidth, final StringBuilder sb) {
    sb.append("+");
    for (int i = 0; i < maxHeaderWidth + 2; i++) sb.append('-');
    sb.append('+');
    for (int i = 0; i < maxValuesWidth + 2; i++) sb.append('-');
    sb.append('+');
    sb.append('\n');
  }

  /**
   * Builds a <em>card</em> from the given (possibly multiline) headers and values.
   *
   * @param headers the headers
   * @param values the values
   * @return the card
   * @throws IllegalArgumentException if the number of headers is different from the number of
   *     values, or if a header is blank or empty.
   * @throws NullPointerException if headers or values, or an header or value entry is {@code null}.
   */
  public static String card(List<String> headers, List<String> values)
      throws IllegalArgumentException, NullPointerException {

    if (Objects.requireNonNull(headers, "Headers must not be null").isEmpty())
      throw new IllegalArgumentException("There must be at least one value");
    if (headers.size() != Objects.requireNonNull(values, "Values must not be null").size())
      throw new IllegalArgumentException(
          "The number of headers must be the same as the number of values");
    for (String h : headers)
      if (Objects.requireNonNull(h, "No header can be null").isBlank())
        throw new IllegalArgumentException("No header can be blank or empty");
    for (String v : values) Objects.requireNonNull(v, "No value can be null");

    int maxHeaderWidth = maxWidth(headers), maxValuesWidth = maxWidth(values);

    Iterator<String> hit = headers.iterator();
    Iterator<String> vit = values.iterator();
    StringBuilder sb = new StringBuilder();
    hl(maxHeaderWidth, maxValuesWidth, sb);
    while (hit.hasNext()) {
      String[] h = hit.next().split("\n");
      String[] v = vit.next().split("\n");
      final int max = v.length > h.length ? v.length : h.length;
      for (int i = 0; i < max; i++)
        sb.append(
            String.format(
                "| %-" + maxHeaderWidth + "s | %-" + maxValuesWidth + "s |\n",
                i < h.length ? h[i] : "",
                i < v.length ? v[i] : ""));
      hl(maxHeaderWidth, maxValuesWidth, sb);
    }
    return sb.toString();
  }
}
