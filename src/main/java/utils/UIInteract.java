/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.io.Closeable;
import java.io.Console;
import java.util.Scanner;

/**
 * An utility class to interact with the user.
 *
 * <p>This class provides methods to read commands and lines from the user, and to output messages;
 * it has a different behavior if the code is run interactively or not: in the first case, prompts
 * are printed and the user can input commands and lines, in the second case, no prompts are
 * actually printed.
 *
 * <h2>Example</h2>
 *
 * This class can be used to build a simple
 * [REPL](https://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop); consider the code
 *
 * <pre>{@code
 * try (UIInteract ui = UIInteract.getInstance()) {
 *   for (;;) {
 *     String[] input = ui.command("> ");
 *     if (input == null) break;
 *     switch (input[0]) {
 *       case "LS":
 *         ui.output("You requested an ls...");
 *         break;
 *       case "CD":
 *         ui.output("You requested a cd...");
 *         break;
 *       default:
 *         ui.error("Unknown command: " + input[0]);
 *         break;
 *     }
 *   }
 * }
 * }</pre>
 *
 * will lead to an execution like the following
 *
 * <pre>{@code
 * REPL started (console)
 * > ls
 * You requested an ls...
 * > ls
 * You requested an ls...
 * > cd
 * You requested a cd...
 * > foobar
 * Error: Unknown command: FOOBAR
 * >
 * REPL stopped
 * }</pre>
 */
public abstract class UIInteract implements Closeable {

  /**
   * Reads a command from the user.
   *
   * <p>This method outputs the given prompt (that is given by a format and its arguments, like in
   * {@link String#format(String, Object...)}), and then reads a line; it hence splits it in two
   * parts on the first space, and trims anc converts the first part to uppercase.
   *
   * @param format the format of the prompt.
   * @param args the argument to complete the format.
   * @return a two-element array, where the first element is the command, and the second element is
   *     the rest of the line, or {@code null} if the end of the input has been reached.
   */
  public abstract String[] command(String format, Object... args);

  /**
   * Reads a line from the user, after having prompting.
   *
   * <p>This method outputs the given prompt, and then reads a line.
   *
   * @param prompt the prompt.
   * @return the line, or {@code null} if the end of the input has been reached.
   */
  public abstract String line(String prompt);

  /**
   * Reads a line from the user.
   *
   * @return the line, or {@code null} if the end of the input has been reached.
   */
  public abstract String line();

  /**
   * Prompts the user with the given message.
   *
   * @param message the prompt.
   */
  public abstract void prompt(Object message);

  /**
   * Output the given message.
   *
   * @param message the message.
   */
  public abstract void output(Object message);

  /**
   * Outputs the given error message.
   *
   * @param message the message.
   */
  public abstract void error(Object message);

  /**
   * Returns a {@code UIInteract} instance.
   *
   * <p>If the code is run interactively, the interaction is done through {@code System.console()},
   * otherwise through {@code System.in}.
   *
   * @return the instance.
   */
  public static UIInteract getInstance() {
    final Console console = System.console();
    if (console == null) {
      System.err.println("REPL started (stdin)");
      final Scanner s = new Scanner(System.in);
      return new UIInteract() {
        @Override
        public String[] command(String format, Object... args) {
          if (!s.hasNextLine()) return null;
          final String[] parts = s.nextLine().split(" ", 2);
          parts[0] = parts[0].trim().toUpperCase();
          return parts;
        }

        @Override
        public String line(String prompt) {
          return s.hasNextLine() ? s.nextLine() : null;
        }

        @Override
        public String line() {
          return line(null);
        }

        @Override
        public void prompt(Object message) {}

        @Override
        public void output(Object message) {
          System.out.println(message);
        }

        @Override
        public void error(Object message) {
          System.err.println(message);
        }

        @Override
        public void close() {
          s.close();
          System.err.println("REPL stopped");
        }
      };
    } else {
      console.writer().println("REPL started (console)");
      return new UIInteract() {
        @Override
        public String[] command(String format, Object... args) {
          final String line = console.readLine(format, args);
          if (line == null) return null;
          final String[] parts = line.split(" ", 2);
          parts[0] = parts[0].trim().toUpperCase();
          return parts;
        }

        @Override
        public String line(String prompt) {
          return console.readLine(prompt);
        }

        @Override
        public String line() {
          return console.readLine();
        }

        @Override
        public void prompt(Object message) {
          console.writer().println(message);
        }

        @Override
        public void output(Object message) {
          console.writer().println(message);
        }

        @Override
        public void error(Object message) {
          console.writer().println("Error: " + message);
        }

        @Override
        public void close() {
          console.flush();
          System.err.println("REPL stopped");
        }
      };
    }
  }
}
