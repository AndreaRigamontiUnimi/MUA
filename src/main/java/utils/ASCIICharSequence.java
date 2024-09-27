/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/** An implementation of {@link CharSequence} that contains ASCII characters. */
public class ASCIICharSequence implements CharSequence {

  private final String value;

  /**
   * Checks if the given string is ASCII.
   *
   * @param data the string to check.
   * @return if the string non {@code null} and contains only ASCII characters.
   */
  public static boolean isAscii(final String data) {
    return data != null && StandardCharsets.US_ASCII.newEncoder().canEncode(data);
  }

  private ASCIICharSequence(final String data) {
    if (!isAscii(data))
      throw new IllegalArgumentException("ASCIICharSequence value must be ASCII (and not null)");
    this.value = data;
  }

  /**
   * Constructs an {@link ASCIICharSequence} given a string.
   *
   * @param data the string.
   * @return the sequence.
   * @throws IllegalArgumentException if the string is not ASCII or {@code null}.
   */
  public static ASCIICharSequence of(final String data) throws IllegalArgumentException {
    return new ASCIICharSequence(data);
  }

  /**
   * Constructs an {@link ASCIICharSequence} given a byte array.
   *
   * @param bytes the byte array.
   * @return the sequence.
   * @throws NullPointerException if the byte array contains non ASCII bytes or is {@code null}.
   */
  public static ASCIICharSequence of(final byte[] bytes) {
    return new ASCIICharSequence(
        new String(Objects.requireNonNull(bytes), StandardCharsets.US_ASCII));
  }

  @Override
  public int length() {
    return value.length();
  }

  @Override
  public char charAt(int index) {
    return value.charAt(index);
  }

  @Override
  public ASCIICharSequence subSequence(int start, int end) {
    return new ASCIICharSequence(value.substring(start, end));
  }

  /**
   * Checks if this sequence is equal to another, ignoring case.
   *
   * @param other the other sequence.
   * @return if this sequence is equal to the other, ignoring case.
   */
  public boolean equalsIgnoreCase(ASCIICharSequence other) {
    return value.equalsIgnoreCase(other.value);
  }

  /**
   * Returns the position of the given character.
   *
   * @param ch the character.
   * @return the first position of the character, or -1 if the character is not present.
   */
  public int indexOf(char ch) {
    return value.indexOf(ch);
  }

  /**
   * Returns a subsequence of this sequence.
   *
   * @param start the index of the beginning of the subsequence.
   * @return the subsequence.
   * @throws IndexOutOfBoundsException if the start index is negative or greater than the length of
   *     this sequence.
   */
  public ASCIICharSequence subSequence(int start) throws IndexOutOfBoundsException {
    return new ASCIICharSequence(value.substring(start));
  }

  /**
   * Returns the ASCII bytes of this sequence.
   *
   * @return the bytes.
   */
  public byte[] getASCIIBytes() {
    return value.getBytes(StandardCharsets.US_ASCII);
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof String) return value.equals(obj);
    if (!(obj instanceof ASCIICharSequence)) return false;
    return value.equals(((ASCIICharSequence) obj).value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
