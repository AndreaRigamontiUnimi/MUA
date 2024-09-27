/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * An utility class to decode multipart encoded mail messages.
 *
 * <h2>Example</h2>
 *
 * Suppose that the {@code ASCIICharSequence} {@code rawMessage} contains the following sequence:
 *
 * <pre>{@code
 * From: Massimo Santini <santini@di.unimi.it>
 * To: prigioniero@di.unimi.it, "Ing. Carlo Rossi" <cr@architettura.it>
 * Subject: =?utf-8?B?UXVlc3RhIMOoIHVuYSBwcm92YQ==?=
 * Date: Wed, 6 Dec 2023 19:22:29 +0100
 * MIME-Version: 1.0
 * Content-Type: multipart/alternative; boundary=frontier
 *
 * This is a message with multiple parts in MIME format.
 * --frontier
 * Content-Type: text/plain; charset="utf-8"
 * Content-Transfer-Encoding: base64
 *
 * UXVlc3RhIHBhcnRlIMOoIHVuIGVzZW1waW8=
 * --frontier
 * Content-Type: text/html; charset="utf-8"
 * Content-Transfer-Encoding: base64
 *
 * UXVlc3RhIHBhcnRlIMOoIGluIDxzdHJvbmc+aHRtbDwvaHRtbD4h
 * --frontier--
 * }</pre>
 *
 * Then the following code
 *
 * <pre>{@code
 * List<Fragment> fragments = EntryEncoding.decode(sequence);
 * for (Fragment fragment : fragments) {
 *   System.out.println("Fragment\n\tRaw headers:");
 *   for (List<ASCIICharSequence> rawHeader : fragment.rawHeaders())
 *     System.out.println("\t\tRaw type = " + rawHeader.get(0) + ", value = " + rawHeader.get(1));
 *   System.out.println("\tRaw body: \n\t\t" + fragment.rawBody() + "\n");
 * }
 * }</pre>
 *
 * produces the output
 *
 * <pre>{@code
 * Fragment
 *         Raw headers:
 *                 Raw type = from, value = Massimo Santini <santini@di.unimi.it>
 *                 Raw type = to, value = prigioniero@di.unimi.it, "Ing. Carlo Rossi" <cr@architettura.it>
 *                 Raw type = subject, value = =?utf-8?B?UXVlc3RhIMOoIHVuYSBwcm92YQ==?=
 *                 Raw type = date, value = Wed, 6 Dec 2023 19:22:29 +0100
 *                 Raw type = mime-version, value = 1.0
 *                 Raw type = content-type, value = multipart/alternative; boundary=frontier
 *         Raw body:
 *                 This is a message with multiple parts in MIME format.
 *
 * Fragment
 *         Raw headers:
 *                 Raw type = content-type, value = text/plain; charset="utf-8"
 *                 Raw type = content-transfer-encoding, value = base64
 *         Raw body:
 *                 UXVlc3RhIHBhcnRlIMOoIHVuIGVzZW1waW8=
 *
 * Fragment
 *         Raw headers:
 *                 Raw type = content-type, value = text/html; charset="utf-8"
 *                 Raw type = content-transfer-encoding, value = base64
 *         Raw body:
 *                 UXVlc3RhIHBhcnRlIMOoIGluIDxzdHJvbmc+aHRtbDwvaHRtbD4h
 * }</pre>
 */
public class EntryEncoding {

  private EntryEncoding() {}

  private static class RawBodyBuilder {

    private final List<ASCIICharSequence> lines = new ArrayList<>();

    private void add(final ASCIICharSequence line) {
      lines.add(Objects.requireNonNull(line));
    }

    private ASCIICharSequence rawBody() {
      return ASCIICharSequence.of(String.join("\n", lines));
    }
  }

  private static class rawHeadersBuilder {
    private final Map<ASCIICharSequence, RawHeader> headers = new LinkedHashMap<>();

    private final void add(final ASCIICharSequence line) {
      final RawHeader dup, header = RawHeader.decode(line);
      if ((dup = headers.put(header.name(), header)) != null)
        throw new IllegalArgumentException("Duplicate header: [" + line + "] was [" + dup + "]");
    }

    private String getLowerCaseValue(final String name) {
      final RawHeader header = headers.get(ASCIICharSequence.of(name));
      return header == null ? null : header.value().toString().toLowerCase();
    }

    private List<List<ASCIICharSequence>> rawHeaders() {
      return List.copyOf(
          headers.values().stream().map(RawHeader::rawHeader).collect(Collectors.toList()));
    }
  }

  private record RawHeader(ASCIICharSequence name, ASCIICharSequence value) {
    private RawHeader {
      name = ASCIICharSequence.of(Objects.requireNonNull(name).toString().trim().toLowerCase());
      value = ASCIICharSequence.of(Objects.requireNonNull(value).toString().trim());
    }

    private List<ASCIICharSequence> rawHeader() {
      return List.of(name, value);
    }

    private static RawHeader decode(final ASCIICharSequence line) {
      final String[] parts = Objects.requireNonNull(line).toString().split(":", 2);
      if (parts.length != 2) throw new IllegalArgumentException("Can't parse header: " + line);
      return new RawHeader(ASCIICharSequence.of(parts[0]), ASCIICharSequence.of(parts[1]));
    }
  }

  /**
   * Decodes a multipart encoded mail message.
   *
   * <p>This class, given a sequence obtained by encoding a mail message following RFCs 5322, 2045,
   * and 2047, returns a list of {@link Fragment fragments}, one for every <em>part</em> of the
   * multipart message; it the message is not multipart, it returns a single fragment corresponding
   * to the whole message.
   *
   * @param rawMessage the message.
   * @return a list of {@link Fragment fragments} representing the message.
   */
  public static List<Fragment> decode(final ASCIICharSequence rawMessage) {
    List<Fragment> fragments = new ArrayList<>();
    rawHeadersBuilder rawHeadersBuilder = new rawHeadersBuilder();
    RawBodyBuilder rawBodyBuilder = new RawBodyBuilder();
    ASCIICharSequence separator = null, lastSeparator = null;
    enum Mode {
      HEADERS,
      BODY,
      PART_BODY
    }
    Mode mode = Mode.HEADERS;
    try (final Scanner s = new Scanner(rawMessage.toString())) {
      while (s.hasNextLine()) {
        final ASCIICharSequence line = ASCIICharSequence.of(s.nextLine());
        switch (mode) {
          case HEADERS:
            if (line.isEmpty())
              if (separator == null) {
                final String contentType = rawHeadersBuilder.getLowerCaseValue("content-type");
                if (contentType != null && contentType.startsWith("multipart/alternative")) {
                  final String[] p = contentType.split("boundary=", 2);
                  if (p.length != 2)
                    throw new IllegalArgumentException("Can't determine boundary: " + line);
                  final String boundary = contentType.split("boundary=")[1];
                  separator = ASCIICharSequence.of("--" + boundary);
                  lastSeparator = ASCIICharSequence.of("--" + boundary + "--");
                  mode = Mode.PART_BODY;
                } else mode = Mode.BODY;
              } else mode = Mode.PART_BODY;
            else rawHeadersBuilder.add(line);
            break;
          case BODY:
            rawBodyBuilder.add(line);
            if (!s.hasNextLine()) {
              fragments.add(new Fragment(rawHeadersBuilder.rawHeaders(), rawBodyBuilder.rawBody()));
              rawHeadersBuilder = new rawHeadersBuilder();
              rawBodyBuilder = new RawBodyBuilder();
            }
            break;
          case PART_BODY:
            if (line.equals(separator) || line.equals(lastSeparator)) {
              fragments.add(new Fragment(rawHeadersBuilder.rawHeaders(), rawBodyBuilder.rawBody()));
              rawHeadersBuilder = new rawHeadersBuilder();
              rawBodyBuilder = new RawBodyBuilder();
              mode = Mode.HEADERS;
            } else rawBodyBuilder.add(line);
            break;
        }
      }
    }
    return fragments;
  }
}
