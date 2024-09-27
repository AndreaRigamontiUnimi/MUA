/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** Filesystem-based storage. */
public class Storage {

  private static final Path EMPTY_PATH = Path.of("");
  private final Path baseDir;

  /**
   * Creates an instance of {@link #Storage} given the path of a directory.
   *
   * @param baseDir the path of the base directory of the storage.
   * @throws NullPointerException if the path is {@code null}.
   * @throws IllegalArgumentException if the path is not a directory.
   */
  public Storage(String baseDir) throws NullPointerException, IllegalArgumentException {
    this.baseDir = Path.of(Objects.requireNonNull(baseDir)).normalize().toAbsolutePath();
    if (!Files.isDirectory(this.baseDir))
      throw new IllegalArgumentException("Not a directory: " + this.baseDir);
  }

  /**
   * A box.
   *
   * <p>A box is a collection of {@link Entry Entries}.
   */
  public class Box {

    /**
     * An entry.
     *
     * <p>An entry is a sequence of bytes (stored in the filesystem).
     */
    public class Entry {
      private final Path entryPath;

      private Entry(Path path) {
        this.entryPath = baseDir.resolve(boxPath).relativize(path);
      }

      /**
       * Returns the content of this entry.
       *
       * @return the bytes of the entry, or {@code null} if some {@link IOException} occurs.
       */
      public ASCIICharSequence content() {
        try {
          return ASCIICharSequence.of(
              Files.readAllBytes(baseDir.resolve(boxPath).resolve(entryPath)));
        } catch (IOException e) {
          return null;
        }
      }

      /**
       * Deletes this entry.
       *
       * @return if the entry was deleted (from the filesystem), or if some {@link IOException}
       *     occurred.
       */
      public boolean delete() {
        try {
          Files.delete(baseDir.resolve(boxPath).resolve(entryPath));
          return true;
        } catch (IOException e) {
          return false;
        }
      }

      @Override
      public String toString() {
        final String nameExt = entryPath.toString();
        final int idx = nameExt.lastIndexOf('.');
        return idx == -1 ? nameExt : nameExt.substring(0, idx);
      }
    }

    private final Path boxPath;

    private Box(Path path) {
      this.boxPath = baseDir.relativize(path);
    }

    /**
     * Creates a sub-box of this box.
     *
     * @param name the name of the sub-box.
     * @return the sub-box, or {@code null} if some {@link IOException} occurs.
     * @throws IllegalArgumentException if the name is blank or contains the file separator
     *     character.
     * @throws NullPointerException if the name is {@code null}.
     */
    public Box box(String name) throws IllegalArgumentException, NullPointerException {
      if (Objects.requireNonNull(name).isBlank()) throw new IllegalArgumentException();
      if (name.contains(File.separator)) throw new IllegalArgumentException();
      Path path = baseDir.resolve(boxPath).resolve(name);
      try {
        Files.createDirectory(path);
        return new Box(path);
      } catch (IOException e) {
        return null;
      }
    }

    /**
     * Deletes this box.
     *
     * <p>If the box is not empty (it contains entries, or sub-boxes), this method will fail,
     * retuning {@code false}.
     *
     * @return if the sub-box was deleted (from the filesystem), or if some {@link IOException}
     *     occurred.
     */
    public boolean delete() {
      try {
        Files.delete(baseDir.resolve(boxPath));
        return true;
      } catch (IOException e) {
        return false;
      }
    }

    /**
     * Returns the entries contained in this box.
     *
     * @return the entries of this box, or {@code null} if some {@link IOException} occurs.
     */
    public List<Entry> entries() {
      try {
        return Files.list(baseDir.resolve(boxPath))
            .filter(Files::isRegularFile)
            .map(Entry::new)
            .sorted((e1, e2) -> e1.toString().compareTo(e2.toString()))
            .toList();
      } catch (IOException e) {
        return Collections.emptyList();
      }
    }

    /**
     * Creates an entry in this box.
     *
     * @param content the content of the entry.
     * @return the entry, or {@code null} if some {@link IOException} occurs.
     * @throws NullPointerException if the content is {@code null}.
     */
    public Entry entry(ASCIICharSequence content) throws NullPointerException {
      Objects.requireNonNull(content);
      final String name = UUID.randomUUID().toString();
      final Path path = baseDir.resolve(boxPath).resolve(name);
      try {
        Files.write(path, content.getASCIIBytes());
        return new Entry(path);
      } catch (IOException e) {
        return null;
      }
    }

    @Override
    public String toString() {
      if (boxPath.equals(EMPTY_PATH)) return "INBOX";
      return boxPath.toString().replace(File.separator, ":");
    }
  }

  /**
   * Returns the boxes contained in this storage.
   *
   * @return the list of boxes in the storage, or {@code null} if some {@link IOException} occurs.
   */
  public List<Storage.Box> boxes() {
    try {
      return Files.walk(this.baseDir)
          .filter(Files::isDirectory)
          .map(Box::new)
          .sorted((b1, b2) -> b1.toString().compareTo(b2.toString()))
          .toList();
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String toString() {
    return "Storage: " + EMPTY_PATH.toAbsolutePath().relativize(baseDir);
  }
}
