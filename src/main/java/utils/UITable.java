/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * An utility class to build a <em>table</em>.
 *
 * <h2>Example</h2>
 *
 * The code
 *
 * <pre>
 * table(
 *     List.of("First header", "Second header", "Third header"),
 *     List.of(
 *         List.of("A multi\nline value", "", "A value\non\nthree lines"),
 *         List.of("Another value", "On\ntwo lines", "A value")
 *     ),
 *     true, true
 * )
 * </pre>
 *
 * returns the following string
 *
 * <pre>
 * +===+===============+===============+==============+
 * | # | First header  | Second header | Third header |
 * +===+===============+===============+==============+
 * | 1 | A multi       |               | A value      |
 * |   | line value    |               | on           |
 * |   |               |               | three lines  |
 * +---+---------------+---------------+--------------+
 * | 2 | Another value | On            | A value      |
 * |   |               | two lines     |              |
 * +===+===============+===============+==============+
 * </pre>
 */
public class UITable {

  private UITable() {}

  private static void hl(final int[] maxWidth, final char ch, final StringBuilder sb) {
    sb.append('+');
    for (int col = 0; col < maxWidth.length; col++) {
      for (int i = 0; i < maxWidth[col] + 2; i++) sb.append(ch);
      sb.append('+');
    }
    sb.append('\n');
  }

  /**
   * Builds a <em>table</em> from the given headers and (possibly multiline) rows.
   *
   * @param headers the headers.
   * @param rows the rows.
   * @param addIndex if {@code true}, adds an index column as the first column.
   * @param addSeparator if {@code true}, adds a separator between rows.
   * @return the table, or the empty string if rows is empty.
   * @throws IllegalArgumentException if the number of elements in some row is different from the
   *     number of headers, or if a header is blank or empty or contains a newline.
   * @throws NullPointerException if headers or rows, or a row, or an header or row entry is {@code
   *     null}.
   */
  public static String table(
      List<String> headers, List<List<String>> rows, boolean addIndex, boolean addSeparator)
      throws IllegalArgumentException, NullPointerException {
    if (Objects.requireNonNull(headers, "Headers must not be null").isEmpty())
      throw new IllegalArgumentException("There must be at least one column");
    headers = new LinkedList<>(headers);
    for (String h : headers)
      if (Objects.requireNonNull(h, "No header can be null").isBlank() || h.contains("\n"))
        throw new IllegalArgumentException("No header can be blank or empty, or contain a newline");
    if (Objects.requireNonNull(rows, "Rows must not be null").isEmpty()) return "";
    rows = rows.stream().map(row -> (List<String>) (new LinkedList<>(row))).toList();
    for (List<String> row : rows) {
      if (Objects.requireNonNull(row, "No row must be null").size() != headers.size())
        throw new IllegalArgumentException(
            "The number of columns must be the same as the number of headers");
      for (String entry : row) Objects.requireNonNull(entry, "No row entry must be null");
    }
    if (addIndex) {
      headers.add(0, "#");
      int idx = 1;
      for (List<String> row : rows) row.add(0, String.valueOf(idx++));
    }

    int[] maxWidth = new int[headers.size()];
    int tm, col = 0;
    for (String h : headers) maxWidth[col++] = h.length();
    for (List<String> row : rows) {
      col = 0;
      for (String entry : row) {
        for (String l : entry.split("\n"))
          if ((tm = l.length()) > maxWidth[col]) maxWidth[col] = tm;
        col++;
      }
    }

    StringBuilder sb = new StringBuilder();
    hl(maxWidth, '=', sb);
    sb.append("|");
    col = 0;
    for (String h : headers) sb.append(String.format(" %-" + maxWidth[col++] + "s |", h));
    sb.append("\n");
    hl(maxWidth, '=', sb);
    Iterator<List<String>> rit = rows.iterator();
    while (rit.hasNext()) {
      List<String> row = rit.next();
      List<String[]> bl = row.stream().map(s -> s.split("\n")).toList();
      final int max = bl.stream().mapToInt(l -> l.length).max().orElse(0);
      for (int i = 0; i < max; i++) {
        sb.append("|");
        col = 0;
        for (String[] l : bl)
          sb.append(String.format(" %-" + maxWidth[col++] + "s |", i < l.length ? l[i] : ""));
        sb.append("\n");
      }
      if (addSeparator && rit.hasNext()) hl(maxWidth, '-', sb);
    }
    hl(maxWidth, '=', sb);
    return sb.toString();
  }
}
