package pl.coderstrust.invoices.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

  public static List<String> readLinesFromFile(File file) throws FileNotFoundException {
    List<String> line = new ArrayList<>();

    if (!file.exists()) {
      throw new FileNotFoundException(file.getAbsolutePath());
    }

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        line.add(scanner.nextLine());
      }
    }
    return line;
  }

  public static void writeLineToFile(File file, String line) {
    writeLineToFile(file, line, false);
  }

  public static void writeLineToFile(File file, String line, boolean overwrite) {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, !overwrite))) {
      writeLine(bufferedWriter, line);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void writeLinesToFile(File file, List<String> lines) {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false))) {
      for (String line : lines) {
        writeLine(bufferedWriter, line);
      }
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private static void writeLine(BufferedWriter bufferedWriter, String line) throws IOException {
    bufferedWriter.write(line);
    bufferedWriter.newLine();
  }
}