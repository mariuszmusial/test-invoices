package pl.coderstrust.invoices.helper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class FileHelperTest {

  @Test
  void shouldReadSimpleFile() throws FileNotFoundException, UnsupportedEncodingException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(
        URLDecoder.decode(classLoader.getResource("readFileTest").getFile(), "UTF-8"));

    List<String> lines = Arrays.asList("Invoice 1", "Invoice 2", "Invoice 3");
    List<String> result = FileHelper.readLinesFromFile(file);

    assertThat(result, is(lines));
  }

  @Test
  void shouldWriteSimpleFile() throws IOException {
    File testFile = new File("test.txt");

    try {
      List<String> lines = Arrays.asList("Invoice 1", "Invoice 2", "Invoice 3");
      lines.forEach(line -> FileHelper.writeLineToFile(testFile, line));

      List<String> linesFromFile = Files.lines(testFile.toPath()).collect(Collectors.toList());
      assertThat(linesFromFile, is(lines));

    } finally {
      testFile.delete();
    }
  }

  @Test
  void readInvoicesFromFileWhenNoInvoicesInDatabaseShouldReturnEmptyList() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(
        Objects.requireNonNull(URLDecoder.decode(classLoader.getResource("readEmptyFileTest")
            .getFile(), "UTF-8")));

    assertThat(FileHelper.readLinesFromFile(file), is(new ArrayList<>()));
  }

  @Test
  void shouldThrowExceptionWhenFileDoesNotExist() {
    assertThrows(FileNotFoundException.class,
        () -> FileHelper.readLinesFromFile(new File("afilethatdoesnotexist")));
  }
}