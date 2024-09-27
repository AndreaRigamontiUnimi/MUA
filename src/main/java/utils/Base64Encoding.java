/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/** An utility class for encoding and decoding strings using the Base64 encoding scheme. */
public class Base64Encoding {

  private static final String ENCODED_WORD_PREFIX = "=?utf-8?B?";
  private static final String ENCODED_WORD_SUFFIX = "?=";

  private Base64Encoding() {}

  /**
   * Encodes a string containing some data using the Base64 encoding scheme.
   *
   * @param data the data to encode.
   * @return the encoded (multi-line) string.
   * @throws NullPointerException if the data is {@code null}.
   */
  public static String encode(final String data) throws NullPointerException {
    return Base64.getMimeEncoder()
        .encodeToString(Objects.requireNonNull(data).getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Encodes a word using the Base64 encoding scheme.
   *
   * @param word the word to encode.
   * @return the <em>encoded-word</em> according to the RFC 2047.
   * @throws NullPointerException if the word is {@code null}.
   */
  public static String encodeWord(final String word) throws NullPointerException {
    return "=?utf-8?B?"
        + Base64.getMimeEncoder(0, new byte[0])
            .encodeToString(Objects.requireNonNull(word).getBytes(StandardCharsets.UTF_8))
        + "?=";
  }

  /**
   * Decodes a sequence encoded using the Base64 scheme.
   *
   * @param sequence the sequence to decode.
   * @return the decoded data.
   * @throws NullPointerException if the sequence is {@code null}.
   */
  public static String decode(final ASCIICharSequence sequence) throws NullPointerException {
    return new String(
        Base64.getMimeDecoder().decode(Objects.requireNonNull(sequence).toString()),
        StandardCharsets.UTF_8);
  }

  /**
   * Decodes a <em>encoded-word</em> according to the RFC 2047.
   *
   * @param sequence the <em>encoded-word</em> to decode.
   * @return the decoded word, or {@code null} if {@code sequence} was not an <em>encoded-word</em>.
   * @throws NullPointerException if the sequence is {@code null}.
   */
  public static String decodeWord(final ASCIICharSequence sequence) throws NullPointerException {
    final String s = Objects.requireNonNull(sequence).toString();
    if (!s.startsWith(ENCODED_WORD_PREFIX) || !s.endsWith(ENCODED_WORD_SUFFIX)) return null;
    return decode(
        sequence.subSequence(
            ENCODED_WORD_PREFIX.length(), s.length() - ENCODED_WORD_SUFFIX.length()));
  }
}
